package com.chat.demo.service.rag.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;

//import org.springframework.ai.vectorstore.SearchRequest;
//import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import com.chat.demo.dto.ChatRequest;
import com.chat.demo.dto.ChatResponse;
import com.chat.demo.model.DocumentChunk;
import com.chat.demo.model.DocumentStored;

import com.chat.demo.repository.DocumentChunkRepository;
import com.chat.demo.repository.DocumentRepository;
import com.chat.demo.service.rag.RagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RagServiceImpl implements RagService {

     // private final VectorStore vectorStore; 
	//lo descomentare luego de haber configurado docker con la extension pgvector // ya instale la extension pgvector en pgadmin

    private final ChatClient chatClient;
    private final DocumentRepository documentRepository;
    private final DocumentChunkRepository chunkRepository;

  
    @Override
    public void ingestDocument(Long documentId) {

        DocumentStored document = documentRepository.findById(documentId)
                .orElseThrow(() ->
                        new RuntimeException("Document not found"));
        
        System.out.println("PATH GUARDADO = " + document.getFilePath());

        Path path = Paths.get(document.getFilePath());

        System.out.println("EXISTE = " + Files.exists(path));
        System.out.println("ABSOLUTE = " + path.toAbsolutePath());

        PagePdfDocumentReader reader =
                new PagePdfDocumentReader(document.getFilePath());

        List<Document> docs = reader.read();

        docs = new TokenTextSplitter().apply(docs);

        docs.forEach(doc -> {

            doc.getMetadata().put("organizationId",
                    document.getOrganization().getId());

            doc.getMetadata().put("areaId",
                    document.getArea().getId());

            doc.getMetadata().put("documentId",
                    document.getId());

            doc.getMetadata().put("fileName",
                    document.getFileName());
        });
        
        AtomicInteger index = new AtomicInteger(0);
/*
        docs.forEach(doc -> {

            String embeddingId =
                    "doc_" + document.getId()
                    + "_chunk_" + index.get();

            DocumentChunk chunk =
                    DocumentChunk.builder()
                    .document(document)
                    .content(doc.getText())
                    .chunkIndex(index.get())
                    .embeddingId(embeddingId)
                    .build();

            chunkRepository.save(chunk);
            index.incrementAndGet();
        });
        //vectorStore.add(docs);     DESCOMENTAR CUANDO PAGUE OPENAI 
        if (vectorStore != null) {
            vectorStore.add(docs);
        }
        */
        
        
        docs.forEach(doc -> {

            String embeddingId =
                    "doc_" + document.getId()
                    + "_chunk_" + index.get();

            DocumentChunk chunk =
                    DocumentChunk.builder()
                    .document(document)
                    .content(doc.getText())
                    .chunkIndex(index.get())
                    .embeddingId(embeddingId)
                    .build();

            chunkRepository.save(chunk);
            index.incrementAndGet();
        });

        // vectorStore.add(docs);
    }

    @Override
    public List<Document> searchRelevantChunks(
            String question,
            Long organizationId) {

        return chunkRepository
                .findTop5ByDocumentOrganizationId(organizationId)
                .stream()
                .map(chunk -> new Document(chunk.getContent()))
                .toList();
    }
    /*
     * DESCOMENTAR CUANDO PAGUE OPENAI
    public List<Document> searchRelevantChunks(String question, Long organizationId) {

    	SearchRequest request = SearchRequest.builder()
    	        .query(question)
    	        .topK(5)
    	        .similarityThreshold(0.5)
    	        .filterExpression(
    	            "organizationId == " + organizationId
    	        )
    	        .build();

        //return vectorStore.similaritySearch(request);
        return List.of();
    }
    
    */

    @Override
    public String buildContext(List<Document> docs) {

        return docs.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public ChatResponse ask(ChatRequest request) {

        List<Document> docs =
                searchRelevantChunks(request.getQuestion(), request.getOrganizationId());

        String context = buildContext(docs);

        String prompt = """
        		CONTEXTO:
        		%s

        		PREGUNTA:
        		%s
        		""".formatted(context, request.getQuestion());

        String answer = chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();

        ChatResponse response = new ChatResponse();

        response.setAnswer(answer);
       // response.setAnswer("IA temporalmente no disponible");
        return response;
    }
    
   
   /*
    public ChatResponse ask(ChatRequest request) {

        String answer = chatClient
                .prompt()
                .user(request.getQuestion())
                .call()
                .content();

        ChatResponse response = new ChatResponse();
        response.setAnswer(answer);

        return response;
    }
    */
    /**
     * 
     *  EL PIPELINE PARA PROBAR ARQUITECTURA ES : (antes de openai emmbeddings)
     *  
Frontend
   ↓
Controller
   ↓
RagService.ask()
   ↓
 Groq
   ↓
Respuesta
     *  
     *  
     *  
     *  
     *  
     *  
     *  **/
    
    
    
     /** El pipeline completo quedó: (cuando se integre con openai)

UPLOAD PDF
    ↓
DocumentService.upload()
    ↓
guardar archivo físico
    ↓
guardar metadata SQL
    ↓
RagService.ingestDocument()
    ↓
PDF Reader
    ↓
Chunking
    ↓
Embeddings
    ↓
PGVector

     */ 
    
}


