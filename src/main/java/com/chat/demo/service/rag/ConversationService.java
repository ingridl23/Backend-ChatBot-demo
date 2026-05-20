package com.chat.demo.service.rag;
import java.util.List;
import java.util.Optional;


import com.chat.demo.model.Conversation;



public interface ConversationService {

	   Conversation save(Conversation conversation);

	    Conversation createConversation(Long userId, String title);

	    Conversation update(Long id,Conversation request);

	    Optional<Conversation> findById(Long id);

	    Optional<Conversation> findByTitle(String title);

	    List<Conversation> findByUser(Long userId);

	    List<Conversation> findActiveByUser(Long userId);

	    void closeConversation(Long id);

	    void delete(Long id);
}
