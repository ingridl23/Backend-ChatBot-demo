package com.chat.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.demo.model.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, Long>{

	Optional<Conversation>findByConversationTitle(String title);
}