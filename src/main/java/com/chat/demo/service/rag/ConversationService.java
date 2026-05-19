package com.chat.demo.service.rag;

import java.util.List;
import java.util.Optional;

import com.chat.demo.model.Conversation;
import com.chat.demo.model.User;

public interface ConversationService {

	  Conversation save(Conversation conversation);

	    Conversation createConversation(User user, String title);

	    Conversation update(Long id, Conversation conversation);

	    Optional<Conversation> findById(Long id);

	    Optional<Conversation> findByTitle(String title);

	    List<Conversation> findByUser(Long userId);

	    List<Conversation> findActiveByUser(Long userId);

	    void closeConversation(Long id);

	    void delete(Long id);
}
