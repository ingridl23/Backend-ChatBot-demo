package com.chat.demo.service.rag;

import java.util.List;
//import java.util.List;
import java.util.Optional;

import com.chat.demo.model.Document;

public interface DocumentService {
	
	    Document save(Document document);

	    Document update(Long id, Document document);

	    Optional<Document> findById(Long id);

	    Optional<Document> findByTitle(String title);

	    Optional<Document> findByOrganization(Long organizationId);

	    Optional<Document> findByArea(Long areaId);

	    Optional<Document> findByUploadedBy(Long userId);

	    void delete(Long id);
	    
	    List<Document> findAllDocuments();

}
