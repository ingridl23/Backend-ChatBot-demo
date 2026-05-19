package com.chat.demo.service.rag.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.chat.demo.model.Conversation;
import com.chat.demo.model.User;
import com.chat.demo.repository.ConversationRepository;
import com.chat.demo.service.rag.ConversationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService{

	private final ConversationRepository conversationRepository;
	
	@Override
	public Conversation save(Conversation conversation) {
		 return conversationRepository.save(conversation);
	}

	@Override
	public Conversation createConversation(User user, String title) {
		 Conversation conversation = Conversation.builder()
	                .user(user)
	                .title(title)
	                .status(true)
	                .createdAt(LocalDateTime.now())
	                .updatedAt(LocalDateTime.now())
	                .build();

	        return conversationRepository.save(conversation);
	}

	@Override
	public Conversation update(Long id, Conversation conversation) {
		   Conversation existing = conversationRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Conversation not found"));

	        existing.setTitle(conversation.getTitle());
	        existing.setStatus(conversation.getStatus());
	        existing.setUpdatedAt(LocalDateTime.now());

	        return conversationRepository.save(existing);
	}

	@Override
	public Optional<Conversation> findById(Long id) {
		  return conversationRepository.findById(id);
	}

	@Override
	public Optional<Conversation> findByTitle(String title) {
		  return conversationRepository.findByTitle(title);
	}

	@Override
	public List<Conversation> findByUser(Long userId) {
		  return conversationRepository.findByUserId(userId);
	}

	@Override
	public List<Conversation> findActiveByUser(Long userId) {
		 return conversationRepository.findByUserIdAndStatusTrue(userId);
	}

	@Override
	public void closeConversation(Long id) {
		 Conversation conversation = conversationRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Conversation not found"));

	        conversation.setStatus(false);
	        conversation.setUpdatedAt(LocalDateTime.now());

	        conversationRepository.save(conversation);
		
	}

	@Override
	public void delete(Long id) {
		 conversationRepository.deleteById(id);
		
	}

}
