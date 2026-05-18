package com.chat.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.demo.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{

	Optional<Message>findByMessageTittle(String tittle);
}
