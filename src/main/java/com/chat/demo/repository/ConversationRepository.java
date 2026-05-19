package com.chat.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.demo.model.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, Long>{

	Optional<Conversation>findByTitle(String title);
	List<Conversation> findByUserId(Long userId);

    List<Conversation> findByUserIdAndStatusTrue(Long userId);
}