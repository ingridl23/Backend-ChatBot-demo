package com.chat.demo.service.rag;

import java.util.List;
//import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.chat.demo.dto.DocumentRequest;
import com.chat.demo.dto.DocumentResponse;


public interface DocumentService {
	
	    DocumentResponse save(DocumentRequest document);

	    // callerAreaId == null significa admin general (sin restricción de área)
	    DocumentResponse update(Long id, DocumentRequest document, Long callerAreaId);

	    List<DocumentResponse> findById(Long id);

	    List<DocumentResponse> findByTitle(String title);

	    List<DocumentResponse> findByOrganization(Long organizationId);

	    List<DocumentResponse> findByArea(Long areaId);

	    List<DocumentResponse> findByUploadedBy(Long userId);

	    void delete(Long id, Long callerAreaId);
	    
	    List<DocumentResponse> findAllDocuments();
	    
	    DocumentResponse upload(
	            MultipartFile file,
	            String title,
	            Long organizationId,
	            List<Long> areaIds,
	            Long statusId,
	            Long uploadedById
	    );

}
