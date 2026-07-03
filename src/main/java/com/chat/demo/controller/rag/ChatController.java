package com.chat.demo.controller.rag;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.demo.dto.ChatRequest;
import com.chat.demo.dto.ChatResponse;
import com.chat.demo.model.User;
import com.chat.demo.service.auth.CustomUserDetails;
import com.chat.demo.service.rag.ChatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/ask")
    public ChatResponse ask(@RequestBody ChatRequest request, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        if (user.getOrganization() == null) {
            throw new RuntimeException("Authenticated user has no organization assigned");
        }

        // Override any client-provided values with the authenticated user's actual data
        request.setUserId(user.getId());
        request.setOrganizationId(user.getOrganization().getId());
        request.setAreaId(user.getArea() != null ? user.getArea().getId() : null);

        return chatService.ask(request);
    }
}
