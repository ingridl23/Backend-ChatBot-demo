package com.chat.demo.service.rag.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.chat.demo.model.Integration;
import com.chat.demo.repository.IntegrationRepository;
import com.chat.demo.service.rag.IntegrationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IntegrationServiceImpl implements IntegrationService {

	private final  IntegrationRepository  integraRepo;
	@Override
	public Integration save(Integration integration) {
		return integraRepo.save(integration);
	}

	
	@Override
	public Integration update(Long id, Integration integration) {

        Integration existing = integraRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Integration not found"));

        existing.setName(integration.getName());
        existing.setBaseUrl(integration.getBaseUrl());
        existing.setApiKey(integration.getApiKey());
        existing.setIsActive(integration.getIsActive());
        existing.setType(integration.getType());

        return integraRepo.save(existing);
    
	}

	@Override
	public Optional<Integration> findById(Long id) {
	
		 return integraRepo.findById(id);
	}

	@Override
	public Optional<Integration> findByName(String name) {
	
		return integraRepo.findByName(name);
	}

	@Override
	public List<Integration> findByOrganization(Long organizationId) {
		return integraRepo.findByOrganizationId(organizationId);
	}

	/*
	@Override
	public List<Integration> findActiveByOrganization(Long organizationId) {
		
		return integraRepo.findByOrganizationIdAndIsActiveTrue(organizationId);
	}
*/
	@Override
	public void deactivate(Long id) {
	
		Integration integration = integraRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Integration not found"));

        integration.setIsActive(false);
        integraRepo.save(integration);
		
	}

	@Override
	public void delete(Long id) {
		integraRepo.deleteById(id);
		
	}

}
