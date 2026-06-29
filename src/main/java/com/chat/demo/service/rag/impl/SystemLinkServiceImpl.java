package com.chat.demo.service.rag.impl;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chat.demo.model.SystemLink;
import com.chat.demo.repository.SystemRepository;
import com.chat.demo.service.rag.SystemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SystemLinkServiceImpl implements SystemService {
	
	private final SystemRepository sysRepo;
	
	
	@Override
	public SystemLink save(SystemLink systemLink) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SystemLink update(Long id, SystemLink systemLink) {
		 SystemLink existing = sysRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("System not found"));

	        existing.setName(systemLink.getName());
	        existing.setUrl(systemLink.getUrl());
	        existing.setDescription(systemLink.getDescription());
	        existing.setIsActive(systemLink.getIsActive());

	        return sysRepo.save(existing);
	}

	@Override
	public Optional<SystemLink> findById(Long id) {
		return  sysRepo.findById(id);
	}

	@Override
	public Optional<SystemLink> findByName(String name) {
		return sysRepo.findByName(name);
	}

	@Override
	public List<SystemLink> findByOrganization(Long organizationId) {
		return sysRepo.findByOrganizationId(organizationId);
	}

	@Override
	public List<SystemLink> findByArea(Long areaId) {
		
		return sysRepo.findByAreaId(areaId);
	}

	@Override
	public List<SystemLink> findActiveByOrganization(Long organizationId) {
		
		return sysRepo.findByOrganizationIdAndIsActiveTrue(organizationId);
	}

	@Override
	public void desactivate(Long id) {
		  SystemLink system = sysRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("System not found"));

	        system.setIsActive(false);
	        sysRepo.save(system);
		
	}

	@Override
	public void delete(Long id) {
	
		sysRepo.deleteById(id);
		
	}

}
