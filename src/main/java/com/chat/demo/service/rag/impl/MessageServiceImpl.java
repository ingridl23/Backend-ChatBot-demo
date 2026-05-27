package com.chat.demo.service.rag.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.chat.demo.dto.MessageRequest;
import com.chat.demo.mapper.MessageMapper;
//import com.chat.demo.model.AISettings;
//import com.chat.demo.model.DocumentStatus;


import com.chat.demo.model.Message;
//import com.chat.demo.model.MessageStatus;
import com.chat.demo.repository.MessageRepository;
import com.chat.demo.service.rag.MessageService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{
	
	public final MessageRepository mensajeRepo;

	public final MessageMapper mapper;
	
	
	@Override
	public MessageRequest save(MessageRequest message) {
		
		 Message entity =
	                mapper.toEntity(message);

	        entity.setCreatedAt(LocalDateTime.now());
	        entity.setUpdatedAt(LocalDateTime.now());

	        Message saved =
	                mensajeRepo.save(entity);

	        return mapper.toResponse(saved);
		
	}

	
	@Override
	public MessageRequest update(Long id, MessageRequest message) {
		
		Message entity = mensajeRepo.findById(id)
		        .orElseThrow(() ->
		                new RuntimeException("AI settings not found"));

		entity.setTitle(message.getTitle());
		entity.setContent(message.getContent());
		entity.setMetadata(message.getMetadata());
		
		entity.setUpdatedAt(LocalDateTime.now());
		
		
	    if (message.getStatus() != null) {

	        Message existingMessage =
	                mensajeRepo.findByStatus(message.getStatus())
	                .orElseThrow(() ->
	                        new RuntimeException("Message status not found"));

	        entity.setStatus(message.getStatus());
	    }
		   Message saved =
	                mensajeRepo.save(entity);

	        return mapper.toResponse(saved);
		
		/************************************************/
	
	}

	@Override
	public Optional<MessageRequest> findById(Long id) {
		
		return mensajeRepo.findById(id) .map(mapper::toResponse);
	}

	@Override
	public Optional<MessageRequest> findByTitle(String title) {
		 return mensajeRepo.findByTitle(title) .map(mapper::toResponse);
	}

	@Override
	public List<MessageRequest> findByConversation(Long conversationId) {
		return mensajeRepo.findByConversationId(conversationId) .stream()
	            .map(mapper::toResponse)
	            .toList();
	}

	@Override
	public List<MessageRequest> findByUser(Long userId) {
		 return mensajeRepo.findByUserId(userId) .stream()
		            .map(mapper::toResponse)
		            .toList();
	}

	@Override
	public void delete(Long id) {
		 mensajeRepo.deleteById(id);
		
	}

}
