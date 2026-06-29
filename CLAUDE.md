# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Spring Boot 3.3.5 backend for a multi-tenant chatbot platform with RAG (Retrieval-Augmented Generation). Organizations upload PDF documents that are chunked and used as context for AI-powered chat. Currently using Groq's API via OpenAI-compatible endpoint (free tier for development), with pgvector/OpenAI embeddings integration commented out pending OpenAI credits.

## Commands

```bash
# Run the application (requires PostgreSQL running)
./mvnw spring-boot:run

# Build (skip tests)
./mvnw clean package -DskipTests

# Run all tests
./mvnw test

# Run a specific test class
./mvnw test -Dtest=SpringChatBotDemoApplicationTests

# Start the PostgreSQL database (with pgvector extension)
docker compose -f docker-composer.yml up -d
```

## Required Environment Variables

```
POSTGRES_USER=postgres
POSTGRES_PASSWORD=<password>
GROQ_API_KEY=<key>
# Future: OPENAI_API_KEY=<key>
```

## Architecture

### Multi-tenant Domain Model

The core domain is multi-tenant: `Organization` → `Area` → `User`. Users belong to an organization and an area. Documents and conversations are scoped to organizations.

```
Organization
├── Areas (departments/specialties)
│   └── Users
├── Documents (PDFs for RAG)
├── Integrations (external system links)
└── AISettings (per-org AI configuration)

Conversation → Messages (USER/BOT sender types)
```

### RAG Pipeline

**Current state (Groq, no embeddings):**
```
POST /api/documents/upload
  → DocumentServiceImpl.upload()     # saves file to disk, persists metadata
  → RagService.ingestDocument()      # reads PDF, chunks with TokenTextSplitter
  → DocumentChunk saved to Postgres  # chunks stored in SQL (no vectors yet)

POST /api/chat/ask
  → ChatServiceImpl → RagServiceImpl.ask()
  → findTop5ByDocumentOrganizationId()  # keyword retrieval (no similarity search)
  → Groq API (llama-3.3-70b-versatile via OpenAI-compatible endpoint)
```

**Planned full pipeline (requires OpenAI credits + pgvector):**
- Uncomment `VectorStore` in `RagServiceImpl`, `spring-ai-starter-vector-store-pgvector`, and pgvector schema init
- Switch `spring.ai.openai.base-url` back to OpenAI proper and set model to `gpt-4o-mini`
- Enable `spring.ai.vectorstore.pgvector.initialize-schema=true`

### Package Structure

```
com.chat.demo
├── config/          # AIConfig (ChatClient bean)
├── controller/
│   ├── auth/        # AuthController, RoleController, PermissionController
│   └── rag/         # ChatController, DocumentController, OrganizationController,
│                    # ConversationController, MessageController, FaqController,
│                    # IntegrationController, AISettingsController
├── service/
│   ├── auth/        # UserService, RoleService, PermissionService, CustomUserDetails
│   │   └── impl/
│   └── rag/         # RagService, ChatService, DocumentService, ConversationService,
│       │            # MessageService, FaqService, OrganizationService, AreaService,
│       │            # AISettingsService, IntegrationService, SystemService, etc.
│       └── impl/
├── model/           # JPA entities
├── repository/      # Spring Data JPA repositories
├── dto/             # Request/Response DTOs
├── mapper/          # Manual mappers (entity ↔ DTO)
└── security/config/ # JwtFilter, SecurityConfig
```

### Security

Stateless JWT auth. Filter chain:
- `/api/auth/**` — public (login)
- `/api/users/**`, `/api/roles/**`, `/api/permissions/**` — ADMIN only
- `/api/documents/**`, `/api/chat/**` — ADMIN or USER
- All other endpoints — authenticated

`@PreAuthorize` annotations on controllers provide method-level enforcement on top of the filter chain. Roles are stored in `users_roles` join table.

### Key Design Decisions

- **No mapstruct** — mappers are written by hand in `mapper/` package
- **PgVector autoconfiguration is excluded** (`spring.autoconfigure.exclude=...PgVectorStoreAutoConfiguration`) to avoid bean wiring failures until embeddings are activated
- **PDF storage path** configured via `app.storage.documents-path=documents` (relative to working directory); PDFs are saved to disk and path stored in `DocumentStored.filePath`
- **`RagService` is downstream of `DocumentService`** — `DocumentService.upload()` calls `ragService.ingestDocument()` after persisting metadata; there is no separate `/rag/ingest` endpoint
- **Swagger UI** available at `/swagger-ui.html` (springdoc-openapi 2.5.0)
