package com.chat.demo.service.rag;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.chat.demo.dto.BrandingFile;
import com.chat.demo.dto.OrganizationRequest;
import com.chat.demo.model.Organization;

public interface OrganizationService {
	
	    OrganizationRequest save(OrganizationRequest organization);

	    OrganizationRequest update(Long id, OrganizationRequest organization);

	    Optional<OrganizationRequest> findById(Long id);

	    Optional<OrganizationRequest> findByName(String name);

	    Optional<OrganizationRequest> findByDomain(String domain);

	    List<OrganizationRequest> findAll();

	    void delete(Long id);

	    OrganizationRequest uploadLogo(Long id, MultipartFile file);

	    OrganizationRequest uploadFavicon(Long id, MultipartFile file);

	    Optional<BrandingFile> loadLogo(Long id);

	    Optional<BrandingFile> loadFavicon(Long id);

}
