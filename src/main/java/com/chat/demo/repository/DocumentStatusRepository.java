package com.chat.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.chat.demo.model.DocumentStatus;

public interface DocumentStatusRepository extends JpaRepository<DocumentStatus, Long>{

	Optional<DocumentStatus>findByName(String name);

}
