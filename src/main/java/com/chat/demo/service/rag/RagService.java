package com.chat.demo.service.rag;

import org.springframework.ai.document.Document;
import com.chat.demo.dto.ChatRequest;
import com.chat.demo.dto.ChatResponse;
import com.chat.demo.dto.DocumentRequest;
import com.chat.demo.dto.SystemLinkRequest;
import com.chat.demo.dto.IntegrationRequest;

/**
 *  RagService NO debería vivir aislado.

Debería integrarse con:

DocumentService

IntegrationService

SystemLinkService

ConversationService

MessageService

porque el RAG es:

la capa de inteligencia
encima de toda la plataforma.

La idea arquitectónica correcta sería
DocumentService
Responsable de:

CRUD de documentos
metadata
estado
storage
RagService
Responsable de:

embeddings
retrieval
context building
IA
Entonces el flujo real debería ser
DocumentController
      ↓
DocumentService
      ↓
guardar metadata documento
      ↓
RagService.ingestDocument()
      ↓
crear embeddings
      ↓
guardar vectores
O sea:
El RAG depende de DocumentService
NO al revés.

Esto significa que probablemente
NO necesitás un endpoint separado /rag/ingest
Porque la ingesta debería ocurrir cuando:

subís un documento
Ejemplo real
POST
POST /api/documents/upload
 */

import java.util.List;



public interface RagService {

   // void ingestDocument(String filePath);
    void ingestDocument(Long documentId);

    List<Document> searchRelevantChunks(String question, Long organizationId, Long areaId);

    String buildContext(List<Document> docs);

    ChatResponse ask(ChatRequest request);
	 

}