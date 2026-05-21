package com.chat.demo.service.rag;

import org.springframework.stereotype.Service;

import com.chat.demo.dto.ChatRequest;
import com.chat.demo.dto.ChatResponse;

@Service
public interface ChatService {
	ChatResponse ask(ChatRequest request);
	
}
