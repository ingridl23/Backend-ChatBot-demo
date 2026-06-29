package com.chat.demo.service.rag.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.chat.demo.dto.MessageRequest;
import com.chat.demo.mapper.MessageMapper;
import com.chat.demo.model.Conversation;
import com.chat.demo.model.Message;
import com.chat.demo.model.SenderType;
import com.chat.demo.model.User;
import com.chat.demo.repository.ConversationRepository;
import com.chat.demo.repository.MessageRepository;
import com.chat.demo.repository.SenderTypeRepository;
import com.chat.demo.repository.UserRepository;
import com.chat.demo.service.rag.MessageService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository mensajeRepo;
    private final MessageMapper mapper;
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final SenderTypeRepository senderTypeRepository;

    @Override
    public MessageRequest save(MessageRequest message) {
        User user = userRepository.findById(message.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Conversation conversation = conversationRepository.findById(message.getConversationId())
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        SenderType senderType = senderTypeRepository.findById(message.getSenderTypeId())
                .orElseThrow(() -> new RuntimeException("SenderType not found"));

        Message entity = mapper.toEntity(message);
        entity.setUser(user);
        entity.setConversation(conversation);
        entity.setSenderType(senderType);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        return mapper.toResponse(mensajeRepo.save(entity));
    }

    @Override
    public MessageRequest update(Long id, MessageRequest message) {
        Message entity = mensajeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        entity.setTitle(message.getTitle());
        entity.setContent(message.getContent());
        entity.setMetadata(message.getMetadata());
        entity.setUpdatedAt(LocalDateTime.now());

        if (message.getStatus() != null) {
            entity.setStatus(message.getStatus());
        }

        return mapper.toResponse(mensajeRepo.save(entity));
    }

    @Override
    public Optional<MessageRequest> findById(Long id) {
        return mensajeRepo.findById(id).map(mapper::toResponse);
    }

    @Override
    public Optional<MessageRequest> findByTitle(String title) {
        return mensajeRepo.findByTitle(title).map(mapper::toResponse);
    }

    @Override
    public List<MessageRequest> findByConversation(Long conversationId) {
        return mensajeRepo.findByConversationId(conversationId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<MessageRequest> findByUser(Long userId) {
        return mensajeRepo.findByUserId(userId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        mensajeRepo.deleteById(id);
    }
}
