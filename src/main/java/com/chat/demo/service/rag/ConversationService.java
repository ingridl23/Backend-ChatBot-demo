package com.chat.demo.service.rag;
import java.util.List;
import java.util.Optional;

import com.chat.demo.dto.ConversationRequest;




public interface ConversationService {

	    ConversationRequest saveConversation(ConversationRequest conversation);

	  //  ConversationRequest createConversation(Long userId, String title);

	    ConversationRequest updateConversation(Long id,ConversationRequest request);

	    Optional<ConversationRequest> findById(Long id);

	    Optional<ConversationRequest> findByTitle(String title);

	    List<ConversationRequest> findByUser(Long userId);

	    List<ConversationRequest> findActiveByUser(Long userId);

	    void closeConversation(Long id);

	    void delete(Long id);
}
