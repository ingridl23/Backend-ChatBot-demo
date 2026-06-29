package com.chat.demo.service.rag;

import java.util.Optional;

import com.chat.demo.dto.IntegrationRequest;


public interface IntegrationService {

	IntegrationRequest save(IntegrationRequest integration);

	IntegrationRequest update(Long id, IntegrationRequest integration);

    Optional<IntegrationRequest> findById(Long id);

    Optional<IntegrationRequest> findByName(String name);

    Optional<IntegrationRequest> findByOrganization(Long organizationId);

   // List<Integration> findActiveByOrganization(Long organizationId);

    void deactivate(Long id);

    void delete(Long id);
}
