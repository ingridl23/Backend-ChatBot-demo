package com.chat.demo.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.chat.demo.model.Document;


public interface DocumentRepository extends JpaRepository<Document, Long>{

	    Optional<Document>findByTitle(String title);

	    List<Document> findByOrganizationId(Long organizationId);

	    List<Document> findByAreaId(Long areaId);

	    List<Document> findByUploadedById(Long userId);
	
}