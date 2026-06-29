package com.chat.demo.mapper;

import org.springframework.stereotype.Component;
import com.chat.demo.dto.OrganizationRequest;
import com.chat.demo.model.Organization;


@Component
public class OrganizationMapper {

	  public Organization toEntity(OrganizationRequest dto) {

	        return Organization.builder()
	                .name(dto.getName())
	                .logoUrl(dto.getLogoUrl())
	                .primaryColor(dto.getPrimaryColor())
	                .secondaryColor(dto.getSecondaryColor())
	                .faviconUrl(dto.getFaviconUrl())
	                .domain(dto.getDomain())
	                .supportEmail(dto.getSupportEmail())
	            
	                .build();
	    }

	    public OrganizationRequest toResponse(Organization entity) {

	        OrganizationRequest dto = new OrganizationRequest();

	        dto.setName(entity.getName());
	        dto.setLogoUrl(entity.getLogoUrl());
	        dto.setPrimaryColor(entity.getPrimaryColor());
	        dto.setSecondaryColor(entity.getSecondaryColor());
	        dto.setFaviconUrl(entity.getFaviconUrl());
	        dto.setFaviconUrl(entity.getFaviconUrl());
	        dto.setFaviconUrl(entity.getFaviconUrl());
	        dto.setDomain(entity.getDomain());
	        dto.setSupportEmail(entity.getSupportEmail());
	       
	     
	        return dto;
	    }
}
