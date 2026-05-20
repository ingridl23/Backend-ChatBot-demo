package com.chat.demo.mapper;

import org.springframework.stereotype.Component;
import com.chat.demo.dto.IntegrationRequest;
import com.chat.demo.model.Integration;

@Component
public class IntegrationMapper {
	
	
	public Integration toEntity(IntegrationRequest integ) {
		
		 return Integration.builder()
				.name(integ.getName())
				.authType(integ.getAuthType())
				.baseUrl(integ.getBaseUrl())
				.isActive(integ.getIsActive())
				.apiKey(integ.getApyKey())
				.build();
	}
	
	 public IntegrationRequest toResponse(Integration entity) {

	        IntegrationRequest dto = new IntegrationRequest();

	        dto.setName(entity.getName());
	        dto.setAuthType(entity.getAuthType());
	        dto.setBaseUrl(entity.getBaseUrl());
	        dto.setIsActive(entity.getIsActive());
	      
	        return dto;
	    }

}
