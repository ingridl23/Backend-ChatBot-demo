package com.chat.demo.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.chat.demo.model.DocumentStored;


public interface DocumentRepository extends JpaRepository<DocumentStored, Long>{

	    Optional<DocumentStored>findByTitle(String title);

	    List<DocumentStored> findByOrganizationId(Long organizationId);

	    List<DocumentStored> findByAreaId(Long areaId);

	    List<DocumentStored> findByUploadedById(Long userId);
	
}