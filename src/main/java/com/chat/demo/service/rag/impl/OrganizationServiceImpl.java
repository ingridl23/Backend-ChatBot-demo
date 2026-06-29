package com.chat.demo.service.rag.impl;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.chat.demo.dto.OrganizationRequest;
import com.chat.demo.mapper.OrganizationMapper;
import com.chat.demo.model.AISettings;
import com.chat.demo.model.Organization;
import com.chat.demo.repository.OrganizationRepository;
import com.chat.demo.service.rag.OrganizationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService{
	
	private final OrganizationRepository organRepo;
	private final OrganizationMapper  mapper;
	
	@Override
	public OrganizationRequest save(OrganizationRequest organization) {
		
		
		  Organization entity =
	                mapper.toEntity(organization);

	        entity.setCreatedAt(LocalDateTime.now());
	        entity.setUpdatedAt(LocalDateTime.now());

	        Organization saved =
	                organRepo.save(entity);

	        return mapper.toResponse(saved);
		
		
	}

	@Override
	public OrganizationRequest update(Long id, OrganizationRequest organization) {
		  Organization existing = organRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("Organization not found"));

	        existing.setName(organization.getName());
	        existing.setLogoUrl(organization.getLogoUrl());
	        existing.setPrimaryColor(organization.getPrimaryColor());
	        existing.setSecondaryColor(organization.getSecondaryColor());
	        existing.setFaviconUrl(organization.getFaviconUrl());
	        existing.setDomain(organization.getDomain());
	        existing.setSupportEmail(organization.getSupportEmail());

	        return mapper.toResponse(existing);
	}

	@Override
	public Optional<OrganizationRequest> findById(Long id) {
		 return organRepo.findById(id) .map(mapper::toResponse);
	}

	@Override
	public Optional<OrganizationRequest> findByName(String name) {
		return organRepo.findByName(name) .map(mapper::toResponse);
	}

	@Override
	public Optional<OrganizationRequest> findByDomain(String domain) {
	return organRepo.findByDomain(domain) .map(mapper::toResponse);
	}

	@Override
	public List<OrganizationRequest> findAll() {
		return organRepo.findAll().stream()
	            .map(mapper::toResponse)
	            .toList();
		}

	@Override
	public void delete(Long id) {
		 organRepo.deleteById(id);
		
	}

}
