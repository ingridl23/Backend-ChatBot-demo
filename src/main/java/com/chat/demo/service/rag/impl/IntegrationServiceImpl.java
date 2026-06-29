package com.chat.demo.service.rag.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.chat.demo.dto.IntegrationRequest;
import com.chat.demo.model.Integration;
import com.chat.demo.model.IntegrationType;
import com.chat.demo.repository.IntegrationRepository;
import com.chat.demo.repository.IntegrationTypeRepository;
import com.chat.demo.service.rag.IntegrationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IntegrationServiceImpl implements IntegrationService {

    private final IntegrationRepository integraRepo;
    private final IntegrationTypeRepository typeRepo;
    private final com.chat.demo.mapper.IntegrationMapper mapper;

    @Override
    public IntegrationRequest save(IntegrationRequest integration) {
        Integration entity = mapper.toEntity(integration);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        Integration saved = integraRepo.save(entity);
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
        existing.setUpdatedAt(LocalDateTime.now());

        if (integration.getTypeId() != null) {
            IntegrationType type = typeRepo.findById(integration.getTypeId())
                    .orElseThrow(() -> new RuntimeException("Integration type not found"));
            existing.setType(type);
        }

        return mapper.toResponse(integraRepo.save(existing));
    }

    @Override
    public Optional<IntegrationRequest> findById(Long id) {
        return integraRepo.findById(id).map(mapper::toResponse);
    }

    @Override
    public Optional<IntegrationRequest> findByName(String name) {
        return integraRepo.findByName(name).map(mapper::toResponse);
    }

    @Override
    public Optional<IntegrationRequest> findByOrganization(Long organizationId) {
        return integraRepo.findByOrganizationId(organizationId).stream()
                .findFirst()
                .map(mapper::toResponse);
    }

    @Override
    public void deactivate(Long id) {
        Integration integration = integraRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Integration not found: " + id));
        integration.setIsActive(false);
        integration.setUpdatedAt(LocalDateTime.now());
        integraRepo.save(integration);
    }

    @Override
    public void delete(Long id) {
        integraRepo.deleteById(id);
    }
}
