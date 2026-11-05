package com.chat.demo.service.rag;

import org.springframework.stereotype.Service;

import com.chat.demo.dto.ChatRequest;
import com.chat.demo.dto.ChatResponse;

import reactor.core.publisher.Flux;

@Service
public interface ChatService {
	ChatResponse ask(ChatRequest request);
	// Agregar este método a la interfaz ChatService:
	Flux<String> askStream(ChatRequest request);
}
