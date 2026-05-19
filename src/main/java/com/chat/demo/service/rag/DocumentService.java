package com.chat.demo.service.rag;

import java.util.List;
import java.util.Optional;

import com.chat.demo.model.Document;

public interface DocumentService {
	
	 Document save(Document document);

	    Document update(Long id, Document document);

	    Optional<Document> findById(Long id);

	    Optional<Document> findByTitle(String title);

	    List<Document> findByOrganization(Long organizationId);

	    List<Document> findByArea(Long areaId);

	    List<Document> findByUploadedBy(Long userId);

	    void delete(Long id);

}
