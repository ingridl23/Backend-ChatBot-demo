package com.chat.demo.mapper;

import org.springframework.stereotype.Component;

import com.chat.demo.dto.SystemLinkRequest;
import com.chat.demo.model.SystemLink;

@Component
public class SystemLinkMapper {
	  public SystemLink toEntity(SystemLinkRequest dto) {

	        return SystemLink.builder()
	                .name(dto.getName())
	                .url(dto.getUrl())
	                .description(dto.getDescription())
	                .isActive(dto.getIsActive())
	             
	            
	                .build();
	    }

	    public SystemLinkRequest toResponse(SystemLink entity) {

	        SystemLinkRequest dto = new SystemLinkRequest();

	        dto.setName(entity.getName());
	        dto.setUrl(entity.getUrl());
	        dto.setDescription(entity.getDescription());
	        dto.setIsActive(entity.getIsActive());
	       
	     
	        return dto;
	    }
}
