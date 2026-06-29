package com.chat.demo.service.rag;
import java.util.List;
import java.util.Optional;

import com.chat.demo.dto.MessageRequest;


public interface MessageService {

	
	    MessageRequest save(MessageRequest message);

	    MessageRequest update(Long id, MessageRequest message);

	    Optional<MessageRequest> findById(Long id);

	    Optional<MessageRequest> findByTitle(String title);

	    List<MessageRequest> findByConversation(Long conversationId);

	    List<MessageRequest> findByUser(Long userId);

	    void delete(Long id);
	
}
