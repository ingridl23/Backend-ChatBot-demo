package com.chat.demo.service.rag;
import java.util.List;
import java.util.Optional;
import com.chat.demo.model.Organization;

public interface OrganizationService {
	
	    Organization save(Organization organization);

	    Organization update(Long id, Organization organization);

	    Optional<Organization> findById(Long id);

	    Optional<Organization> findByName(String name);

	    Optional<Organization> findByDomain(String domain);

	    List<Organization> findAll();

	    void delete(Long id);

}
