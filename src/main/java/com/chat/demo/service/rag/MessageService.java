package com.chat.demo.service.rag;
import java.util.List;
import java.util.Optional;
import com.chat.demo.model.Message;


public interface MessageService {

	
	    Message save(Message message);

	    Message update(Long id, Message message);

	    Optional<Message> findById(Long id);

	    Optional<Message> findByTitle(String title);

	    List<Message> findByConversation(Long conversationId);

	    List<Message> findByUser(Long userId);

	    void delete(Long id);
	
}
