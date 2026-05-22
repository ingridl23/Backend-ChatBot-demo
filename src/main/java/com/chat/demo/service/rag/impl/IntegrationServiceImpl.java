package com.chat.demo.service.rag.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.chat.demo.dto.IntegrationRequest;
import com.chat.demo.mapper.IntegrationMapper;
import com.chat.demo.model.AISettings;
import com.chat.demo.model.DocumentStatus;
import com.chat.demo.model.Integration;
import com.chat.demo.model.IntegrationType;
import com.chat.demo.repository.IntegrationRepository;
import com.chat.demo.repository.IntegrationTypeRepository;
import com.chat.demo.service.rag.IntegrationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IntegrationServiceImpl implements IntegrationService {

	private final  IntegrationRepository  integraRepo;
	private final IntegrationTypeRepository typeRepo;
	private final IntegrationMapper mapper;
	@Override
	public IntegrationRequest save(IntegrationRequest integration) {
	     Integration entity =
	                mapper.toEntity(integration);

	        entity.setCreatedAt(LocalDateTime.now());
	        entity.setUpdatedAt(LocalDateTime.now());

	        Integration saved =
	                integraRepo.save(entity);

		return mapper.toResponse(saved);
	}

	
	@Override
	public IntegrationRequest update(Long id, IntegrationRequest integration) {

        Integration existing = integraRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Integration not found"));

        existing.setName(integration.getName());
        existing.setBaseUrl(integration.getBaseUrl());
        existing.setApiKey(integration.getApyKey());
        existing.setIsActive(integration.getIsActive());
        

	    if (integration.getTypeId() != null) {

	        IntegrationType type =
	                typeRepo.findById(integration.getTypeId())
	                .orElseThrow(() ->
	                        new RuntimeException("Integration status not found"));
        existing.setType(integration.getTypeId());

        return integraRepo.save(existing);
	    }
    
	}

	@Override
	public Optional<IntegrationRequest> findById(Long id) {
	
		 return integraRepo.findById(id) .map(mapper::toResponse);
	}

	@Override
	public Optional<IntegrationRequest> findByName(String name) {


        return integraRepo.findByName(name).stream()
           .findFirst()
           .map(mapper::toResponse);

		
	}

	@Override
	public Optional<IntegrationRequest> findByOrganization(Long organizationId) {
	
		           return integraRepo.findByOrganizationId(organizationId).stream()
		   	            .findFirst().map(mapper::toResponse);
	}

	
	@Override
	public void deactivate(Long id) {
		
		
		  List<Integration> integration =
	                integraRepo.findByOrganizationId(id);

	        integration.forEach(setting -> setting.setIsActive(false));

	        integraRepo.saveAll(integration);	
		
		/*************************************************/
	
	
	}

	@Override
	public void delete(Long id) {
		integraRepo.deleteById(id);
		
	}

}
