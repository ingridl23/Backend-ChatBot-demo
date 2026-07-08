# Spring ChatBot Demo

Backend de una plataforma de chatbot **multi-tenant** con **RAG** (Retrieval-Augmented Generation). Las organizaciones cargan documentos PDF que se trocean (*chunking*) y se usan como contexto para un chat con IA, con soporte para múltiples áreas, roles de usuario y configuración de tono/comportamiento del bot por organización o por área.

> 💡 El frontend de este proyecto (React + Vite) se desarrolla y versiona por separado, en otro repositorio, y consume esta API vía REST.

---

## Índice

- [Características principales](#características-principales)
- [Stack](#stack)
- [Requisitos](#requisitos)
- [Configuración](#configuración)
- [Cómo correrlo](#cómo-correrlo)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Frontend](#frontend)

## Características principales

| | |
|---|---|
| 🏢 **Multi-tenant** | Organización → Áreas → Usuarios. Documentos y conversaciones quedan scopeados a la organización (y opcionalmente al área). |
| 📄 **RAG sobre PDFs** | Carga de documentos, chunking automático y respuestas del chat en base a la documentación cargada. |
| 🔐 **Autenticación JWT** | Roles (`ADMIN`, `AREA_ADMIN`, `USER`) con autorización a nivel de endpoint y de método. |
| 👥 **Gestión de organización** | Alta de áreas y usuarios, incluida creación de administradores desde el panel de un `ADMIN`. |
| 🎛️ **Configuración de IA por organización/área** | Modelo, temperatura, tokens máximos y un `systemPrompt` personalizable para el tono/saludo del bot, con reglas de seguridad fijas que evitan que el chat alucine o responda con información fuera de la documentación cargada. |
| 📚 **Swagger / OpenAPI** | Documentación interactiva de toda la API. |

## Stack

- **Java 17** · **Spring Boot 3.3.5**
- Spring Security (JWT) · Spring Data JPA
- PostgreSQL (+ extensión `pgvector`, preparada para retrieval semántico a futuro)
- Spring AI, con **Groq** como proveedor de chat (vía su endpoint compatible con OpenAI)
- springdoc-openapi (Swagger UI)
- Maven

## Requisitos

- JDK 17+
- Docker (para levantar PostgreSQL con `pgvector`)
- Una API key de [Groq](https://groq.com/) (nivel gratuito sirve para desarrollo)

## Configuración

Variables de entorno necesarias:

```
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/chatbotdb
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=<tu password>
GROQ_API_KEY=<tu key>
```

> ⚠️ **Nota de seguridad**: las credenciales, API keys y cualquier dato de ejemplo incluidos o referenciados en este repo son solo para pruebas de desarrollo. Si te clonás el repo, generá tus propias credenciales locales (no reutilices las de ejemplo). Si pensás usar este proyecto para algo real o desplegarlo, hacelo en un entorno aparte o en un repositorio privado nuevo, y nunca subas credenciales reales a un repo público.

## Cómo correrlo

```bash
# 1. Levantar PostgreSQL con pgvector
docker compose -f docker-composer.yml up -d

# 2. Correr la aplicación
./mvnw spring-boot:run

# Build sin tests
./mvnw clean package -DskipTests

# Tests
./mvnw test
```

Una vez levantado, la API queda disponible en `http://localhost:8080` y la documentación interactiva en `http://localhost:8080/swagger-ui.html`.

## Estructura del proyecto

```
com.chat.demo
├── config/          # Configuración de beans (ChatClient, etc.)
├── controller/      # Endpoints REST (auth y rag)
├── service/         # Lógica de negocio
├── model/           # Entidades JPA
├── repository/      # Repositorios Spring Data JPA
├── dto/             # DTOs de request/response
├── mapper/          # Mappers manuales entre entidad y DTO
└── security/config/ # Configuración de seguridad (JWT, CORS, roles)
```

Para más detalle de arquitectura y decisiones de diseño, ver [`CLAUDE.md`](./CLAUDE.md).

## Frontend

El frontend (React + Vite) se desarrolla y versiona en un repositorio aparte, y consume esta API vía REST.
