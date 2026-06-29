package com.chat.demo.service.rag.impl;

import org.springframework.stereotype.Service;

import com.chat.demo.dto.ChatRequest;
import com.chat.demo.dto.ChatResponse;
//import com.chat.demo.mapper.MessageMapper;
import com.chat.demo.service.rag.ChatService;

//import com.chat.demo.service.rag.ConversationService;
//import com.chat.demo.service.rag.MessageService;
import com.chat.demo.service.rag.RagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    //private final ConversationService conversationService;
    //private final MessageService messageService;
    //private final  MessageMapper mapper;
    private final RagService ragService;

    @Override
    public ChatResponse ask(ChatRequest request) {

        // IA
    	return ragService.ask(request);

    }
}
