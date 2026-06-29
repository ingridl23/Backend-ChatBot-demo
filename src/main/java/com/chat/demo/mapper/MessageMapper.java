package com.chat.demo.mapper;

import org.springframework.stereotype.Component;
import com.chat.demo.dto.MessageRequest;
import com.chat.demo.model.Message;

@Component
public class MessageMapper {

    public Message toEntity(MessageRequest dto) {
        return Message.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .metadata(dto.getMetadata())
                .status(dto.getStatus())
                .build();
    }

    public MessageRequest toResponse(Message entity) {
        MessageRequest dto = new MessageRequest();
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setMetadata(entity.getMetadata());
        dto.setStatus(entity.getStatus());

        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getId());
        }
        if (entity.getConversation() != null) {
            dto.setConversationId(entity.getConversation().getId());
        }
        if (entity.getSenderType() != null) {
            dto.setSenderTypeId(entity.getSenderType().getId());
        }

        return dto;
    }
}
