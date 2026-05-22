package com.chat.demo.service.rag.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.chat.demo.dto.ConversationRequest;
import com.chat.demo.mapper.ConversationMapper;

import com.chat.demo.model.Conversation;
import com.chat.demo.model.User;
import com.chat.demo.repository.ConversationRepository;
import com.chat.demo.repository.UserRepository;
import com.chat.demo.service.rag.ConversationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService{

	private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
	private final ConversationMapper mapper;
	
	@Override
	public ConversationRequest saveConversation (ConversationRequest conversation) {
		
		Conversation entity = mapper.toEntity(conversation);
	    User user = userRepository.findById(conversation.getUserId())
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    entity.setUser(user);
	    entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        Conversation saved =
                conversationRepository.save(entity);

        return mapper.toResponse(saved);
	}
 
	/*@Override
	public Conversation createConversation(Long userId, String title) {
		 
		  User user = userRepository.findById(userId)
		            .orElseThrow(() -> new RuntimeException("User not found"));
		
		Conversation conversation = Conversation.builder()
	                .user(user)
	                .title(title)
	                .status(true)
	                .createdAt(LocalDateTime.now())
	                .updatedAt(LocalDateTime.now())
	                .build();

	        return conversationRepository.save(conversation);
	}
*/
	
	
	@Override
	public ConversationRequest updateConversation (Long id,ConversationRequest conversation) {
		
		Conversation entity = conversationRepository.findById(id)
		        .orElseThrow(() ->
		                new RuntimeException("Conversation not found"));
		
		if (conversation.getUserId() != null) {

		    User user = userRepository.findById(conversation.getUserId())
		            .orElseThrow(() -> new RuntimeException("User not found"));

		    entity.setUser(user);
		}

		entity.setTitle(conversation.getTitle());
		entity.setStatus(conversation.getStatus());
		entity.setUpdatedAt(LocalDateTime.now());

		  Conversation saved =
	                conversationRepository.save(entity);

	        return mapper.toResponse(saved);
	}
	

	@Override
	public Optional<ConversationRequest> findById(Long id) {

		   return conversationRepository.findById(id)
		            .map(mapper::toResponse);
	}

	@Override
	public Optional<ConversationRequest> findByTitle(String title) {

		   return conversationRepository.findByTitle(title)
		            .map(mapper::toResponse);
	}

	@Override
	public List<ConversationRequest> findByUser(Long userId) {
		  List<Conversation> conversations =
		           conversationRepository.findByUserId(userId);

		    return conversations.stream()
		           
		            .map(mapper::toResponse).toList();
	}

	@Override
	public List<ConversationRequest> findActiveByUser(Long userId) {
		  List<Conversation> conversations =
		            conversationRepository.findByUserIdAndStatusTrue(userId);

		  
		    return conversations
		    		 .stream()
			            .map(mapper::toResponse)
			            .toList();
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
