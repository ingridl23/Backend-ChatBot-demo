package com.chat.demo.mapper;

import org.springframework.stereotype.Component;

import com.chat.demo.dto.DocumentResponse;

import com.chat.demo.dto.DocumentRequest;
import com.chat.demo.model.Area;
import com.chat.demo.model.DocumentStored;



@Component
public class DocumentMapper {

	  public DocumentStored toEntity(DocumentRequest dto) {

	        return DocumentStored.builder()
	                .title(dto.getTitle())
	                .fileName(dto.getFileName())
	                .mimeType(dto.getMimeType())
	                .fileSize(dto.getFileSize())
	                .build();
	    }

	    public DocumentResponse toResponse(DocumentStored entity) {

	        DocumentResponse dto = new DocumentResponse();

	        dto.setId(entity.getId());

	        dto.setTitle(entity.getTitle());

	        dto.setFileName(entity.getFileName());

	        dto.setMimeType(entity.getMimeType());

	        dto.setFileSize(entity.getFileSize());

	        dto.setCreatedAt(entity.getCreatedAt());

	        dto.setUploadedAt(entity.getUploadedAt());

	        if(entity.getStatus() != null) {
	            dto.setStatusId(entity.getStatus().getId());
	        }

	        if(entity.getOrganization() != null) {
	            dto.setOrganizationId(entity.getOrganization().getId());
	        }

	        if(entity.getAreas() != null) {
	            dto.setAreaIds(entity.getAreas().stream().map(Area::getId).toList());
	        }

	        if(entity.getUploadedBy() != null) {
	            dto.setUploadedById(entity.getUploadedBy().getId());
	        }

	        return dto;
	    }
	
	
	
	
	
}
