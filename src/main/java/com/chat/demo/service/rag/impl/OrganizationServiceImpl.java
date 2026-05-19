package com.chat.demo.service.rag.impl;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.chat.demo.model.Organization;
import com.chat.demo.repository.OrganizationRepository;
import com.chat.demo.service.rag.OrganizationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService{
	
	private final OrganizationRepository organRepo;
	
	@Override
	public Organization save(Organization organization) {
		return organRepo.save(organization);
	}

	@Override
	public Organization update(Long id, Organization organization) {
		  Organization existing = organRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("Organization not found"));

	        existing.setName(organization.getName());
	        existing.setLogoUrl(organization.getLogoUrl());
	        existing.setPrimaryColor(organization.getPrimaryColor());
	        existing.setSecondaryColor(organization.getSecondaryColor());
	        existing.setFaviconUrl(organization.getFaviconUrl());
	        existing.setDomain(organization.getDomain());
	        existing.setSupportEmail(organization.getSupportEmail());

	        return organRepo.save(existing);
	}

	@Override
	public Optional<Organization> findById(Long id) {
		 return organRepo.findById(id);
	}

	@Override
	public Optional<Organization> findByName(String name) {
		return organRepo.findByName(name);
	}

	@Override
	public Optional<Organization> findByDomain(String domain) {
	return organRepo.findByDomain(domain);
	}

	@Override
	public List<Organization> findAll() {
		return organRepo.findAll();
		}

	@Override
	public void delete(Long id) {
		 organRepo.deleteById(id);
		
	}

}
