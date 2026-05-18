package com.chat.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.demo.model.Document;



public interface DocumentRepository extends JpaRepository<Document, Long>{

	Optional<Document>findByDocumentTitle(String title);
}