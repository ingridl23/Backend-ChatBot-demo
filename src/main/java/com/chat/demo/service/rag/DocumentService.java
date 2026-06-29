package com.chat.demo.service.rag;

import java.util.List;
//import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.chat.demo.dto.DocumentRequest;
import com.chat.demo.dto.DocumentResponse;


public interface DocumentService {
	
	    DocumentResponse save(DocumentRequest document);

	    DocumentResponse update(Long id, DocumentRequest document);

	    List<DocumentResponse> findById(Long id);

	    List<DocumentResponse> findByTitle(String title);

	    List<DocumentResponse> findByOrganization(Long organizationId);

	    List<DocumentResponse> findByArea(Long areaId);

	    List<DocumentResponse> findByUploadedBy(Long userId);

	    void delete(Long id);
	    
	    List<DocumentResponse> findAllDocuments();
	    
	    DocumentResponse upload(
	            MultipartFile file,
	            String title,
	            Long organizationId,
	            Long areaId,
	            Long statusId,
	            Long uploadedById
	    );

}
