package com.chat.demo.service.rag;


import org.springframework.ai.document.Document;
import com.chat.demo.dto.ChatRequest;
import com.chat.demo.dto.ChatResponse;

import java.util.List;



public interface RagService {

    void ingestDocument(String filePath);

    List<Document> searchRelevantChunks(String question);

    String buildContext(List<Document> docs);

    ChatResponse ask(ChatRequest request);
	 

}