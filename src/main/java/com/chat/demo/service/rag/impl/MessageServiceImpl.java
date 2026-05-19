package com.chat.demo.service.rag.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.chat.demo.model.Message;
import com.chat.demo.repository.MessageRepository;
import com.chat.demo.service.rag.MessageService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{
	
	public final MessageRepository mensajeRepo;
	
	@Override
	public Message save(Message message) {
		 return mensajeRepo.save(message);
	}

	
	@Override
	public Message update(Long id, Message message) {
		
		   Message existing = mensajeRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("Message not found"));

	        existing.setTitle(message.getTitle());
	        existing.setContent(message.getContent());
	        existing.setMetadata(message.getMetadata());
	        existing.setStatus(message.getStatus());

	        return mensajeRepo.save(existing);
	}

	@Override
	public Optional<Message> findById(Long id) {
		
		return mensajeRepo.findById(id);
	}

	@Override
	public Optional<Message> findByTitle(String title) {
		 return mensajeRepo.findByTitle(title);
	}

	@Override
	public List<Message> findByConversation(Long conversationId) {
		return mensajeRepo.findByConversationId(conversationId);
	}

	@Override
	public List<Message> findByUser(Long userId) {
		 return mensajeRepo.findByUserId(userId);
	}

	@Override
	public void delete(Long id) {
		 mensajeRepo.deleteById(id);
		
	}

}
