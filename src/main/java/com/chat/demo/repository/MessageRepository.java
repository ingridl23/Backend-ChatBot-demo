package com.chat.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.demo.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{

	Optional<Message>findByTitle(String title);
	
	List<Message> findByConversationId(Long conversationId);

    List<Message> findByUserId(Long userId);

    List<Message> findByConversationIdOrderByCreatedAtAsc(Long conversationId);// render del chat (historial)
}
