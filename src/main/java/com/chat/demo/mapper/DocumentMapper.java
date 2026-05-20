package com.chat.demo.mapper;

import org.springframework.stereotype.Component;
import com.chat.demo.dto.DocumentRequest;
import com.chat.demo.model.Document;



@Component
public class DocumentMapper {

	  public Document toEntity(DocumentRequest dto) {

	        return Document.builder()
	                .title(dto.getTitle())
	                .fileName(dto.getFileName())
	                .mimeType(dto.getMimeType())
	                .fileSize(dto.getFileSize())
	                .build();
	    }

	    public DocumentRequest toResponse(Document entity) {

	        DocumentRequest dto = new DocumentRequest();

	        dto.setTitle(entity.getTitle());
	        dto.setFileName(entity.getFileName());
	        dto.setMimeType(entity.getMimeType());
	        dto.setFileSize(entity.getFileSize());
	      

	        return dto;
	    }
	
	
	
	
	
}
