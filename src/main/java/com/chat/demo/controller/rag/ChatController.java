package com.chat.demo.controller.rag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.demo.dto.ChatRequest;
import com.chat.demo.dto.ChatResponse;
import com.chat.demo.service.rag.ChatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    
    @PostMapping("/ask")
    public ChatResponse ask(
            @RequestBody ChatRequest request) {

        return chatService.ask(request);
    }
}
