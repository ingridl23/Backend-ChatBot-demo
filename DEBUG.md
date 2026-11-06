# Debug: el chat no responde en producción (Render)

Estado al **2026-11-06**.

## Síntoma original

Después de actualizar a **Spring Boot 3.5.0 / Spring Security 6.5.0** (desde 3.3.5), en producción:

- `POST /api/chat/ask` y `GET /api/users/me` devolvían **403 Forbidden**, aunque `JwtFilter` logueaba `authenticated=true`.
- El controller nunca se ejecutaba y el front mostraba su mensaje de error genérico.

## Bug 1: 403 por `@Order` en `JwtFilter` (RESUELTO, commit `4c09f11`)

### Causa

`JwtFilter` es un `@Component` que extiende `OncePerRequestFilter`. Al agregarle `@Order(Ordered.HIGHEST_PRECEDENCE + 100)`:

1. Spring Boot lo registra **también** como filtro del servlet, y con ese orden corre **antes** del `springSecurityFilterChain` (orden `-100`).
2. Autentica y escribe en el `SecurityContextHolder`. Por eso salía el log `authenticated=true`.
3. Al entrar en la cadena de Spring Security, `SecurityContextHolderFilter` carga un contexto vacío (la app es stateless) y **pisa** el anterior.
4. Dentro de la cadena, `OncePerRequestFilter` ve que el filtro ya corrió y **lo saltea**.
5. `AuthorizationFilter` recibe un usuario anónimo, `hasAnyRole(...)` falla y responde **403**.

Antes del upgrade funcionaba porque el filtro tenía el orden por defecto (`LOWEST_PRECEDENCE`). Corría primero dentro de la cadena, y la ejecución como filtro del servlet era la que se salteaba.

### Fix aplicado

- `JwtFilter`: se quitaron `@Order` y `getOrder()`. Además, el log de authorities pasó de `debug` a `info`.
- `RateLimitFilter`: se quitó `@Order`.
- `SecurityConfig`:
  - Se agregaron `FilterRegistrationBean` con `setEnabled(false)` para ambos filtros, así corren **solo** dentro de la cadena de seguridad.
  - Se reactivó el rate limit, agregándolo **después** del `jwtFilter`:
    ```java
    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
    .addFilterBefore(rateLimitFilter, JwtFilter.class)
    ```
    El error de Security 6.5 *"Filter class JwtFilter does not have a registered order"* aparecía porque la línea del `rateLimitFilter` estaba antes que la del `jwtFilter`.

### Verificación

- Los logs de arranque en Render confirman `Filter jwtFilterRegistration was not registered (disabled)`.
- El front ya **no recibe 403**: ahora recibe **500**, lo que indica que la autenticación pasa.

### Descartado durante el análisis

- Roles: `CustomUserDetails.getAuthorities()` ya agrega el prefijo `ROLE_`.
- Las reglas de `SecurityConfig` para `/api/users/me/**` y `/api/chat/**` incluyen ADMIN, USER y AREA_ADMIN.
- `GlobalExceptionHandler` no interviene en los 403 de los filtros.
- `RateLimitFilter` solo actúa en `POST /api/auth/login`.

## Bug 2: 500 en `POST /api/chat/ask` (ABIERTO)

### Lo que sabemos

- El navegador muestra `Failed to load resource: the server responded with a status of 500`.
- Cualquier excepción en `/ask` termina en el mismo 500 genérico: `GlobalExceptionHandler.handleRuntime` devuelve `"Internal server error"`, y en `application-prod.properties` está `server.error.include-message=never`. El detalle solo queda en los logs.
- **Los logs que revisamos no incluyen ninguna request del front.** Solo aparecen `HEAD /` y `GET /`, que son los chequeos de salud de Render. Si el login y la pregunta hubieran llegado a este backend, habría líneas `JwtFilter processing: POST /api/auth/login` y `POST /api/chat/ask`.

### Hipótesis

1. **Los logs se copiaron antes de hacer la pregunta.** Hay que repetir la pregunta y capturar las líneas posteriores.
2. **El front apunta a otro backend.** El front usa `VITE_API_URL` (`src/services/api.js`). En el repo del front solo existe `.env.development` (`http://localhost:8080/api`), así que el valor de producción está en las variables de entorno del servicio del front en Render. Vite lo fija al compilar, así que si se cambia hay que redesplegar el front.
3. **Falla la llamada a Groq.** Puede ser `GROQ_API_KEY` ausente o inválida (401), el modelo `llama-3.3-70b-versatile` no disponible (404 o 400), o una incompatibilidad del request que arma Spring AI 1.0.9.

### Próximos pasos

1. En DevTools → Network, abrir la request `ask` que da 500:
   - En **Request URL**, confirmar que sea `https://backend-chatbot-demo.onrender.com/api/chat/ask`.
   - En **Response**, ver si el cuerpo es `{"error": "Internal server error", ...}`, que es el formato de nuestro `GlobalExceptionHandler`.
2. En los logs de Render, después de hacer la pregunta, buscar `Error en /ask` o `Unhandled exception` y copiar el stack trace completo, incluidas las líneas `Caused by:`.
3. Verificar en Render, en **Environment**, que `GROQ_API_KEY` esté definida y sea válida.
4. Con el stack trace, corregir la causa real.

## Pendientes una vez resuelto

- Volver a poner en `debug` el log de authorities de `JwtFilter`.
- Evaluar si se quitan los logs `info` de `JwtFilter processing` y `JwtFilter result`, porque generan una línea por cada request.

## Notas de ramas

- **Render despliega desde `master`**, que es la rama por defecto en GitHub.
- `origin/main` es una historia **sin ancestro común** con `master` y quedó en junio (Boot 3.3.5). No tiene este bug y no se usa para el deploy.
