package com.chat.demo.service.rag;

import java.util.List;
//import java.util.List;
import java.util.Optional;

import com.chat.demo.dto.DocumentRequest;


public interface DocumentService {
	
	    DocumentRequest save(DocumentRequest document);

	    DocumentRequest update(Long id, DocumentRequest document);

	    Optional<DocumentRequest> findById(Long id);

	    Optional<DocumentRequest> findByTitle(String title);

	    Optional<DocumentRequest> findByOrganization(Long organizationId);

	    Optional<DocumentRequest> findByArea(Long areaId);

	    Optional<DocumentRequest> findByUploadedBy(Long userId);

	    void delete(Long id);
	    
	    List<DocumentRequest> findAllDocuments();

}
