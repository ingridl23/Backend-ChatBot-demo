package com.chat.demo.service.rag.impl;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.chat.demo.dto.AISettingsRequest;
import com.chat.demo.mapper.AISettingsMapper;
import com.chat.demo.model.AISettings;
import com.chat.demo.repository.AISettingsRepository;
import com.chat.demo.service.rag.AISettingsService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AISettingsServiceImpl implements AISettingsService{

	private final AISettingsRepository aiSettingsRepository;
	  private final AISettingsMapper mapper;
	@Override
	public AISettingsRequest save(AISettingsRequest settings) {
		
		
	       AISettings entity =
	                mapper.toEntity(settings);

	        entity.setCreatedAt(LocalDateTime.now());
	        entity.setUpdatedAt(LocalDateTime.now());

	        AISettings saved =
	                aiSettingsRepository.save(entity);

	        return mapper.toResponse(saved);
	}

	@Override
	public AISettingsRequest update(Long id, AISettingsRequest settings) {
		
		AISettings entity = aiSettingsRepository.findById(id)
		        .orElseThrow(() ->
		                new RuntimeException("AI settings not found"));

		entity.setModelName(settings.getModelName());
		entity.setProvider(settings.getProvider());
		entity.setTemperature(settings.getTemperature());
		entity.setSystemPrompt(settings.getSystemPrompt());
		entity.setMaxTokens(settings.getMaxTokens());
		entity.setActive(settings.getActive());
		entity.setUpdatedAt(LocalDateTime.now());

		   AISettings saved =
	                aiSettingsRepository.save(entity);

	        return mapper.toResponse(saved);
	}

	@Override
	public Optional<AISettingsRequest> findById(Long id) {
		
		
		   return aiSettingsRepository.findById(id)
		            .map(mapper::toResponse);
	}

	@Override
	public Optional<AISettingsRequest> findByOrganizationId(Long organizationId) {
	    List<AISettings> settings =
	            aiSettingsRepository.findByOrganizationId(organizationId);

	    return settings.stream()
	            .findFirst()
	            .map(mapper::toResponse);
	}

	@Override
	public AISettingsRequest getActiveSettings(Long organizationId) {
		AISettings entity = aiSettingsRepository
	            .findByOrganizationIdAndActiveTrue(organizationId)
	            .orElseThrow(() ->
	                    new RuntimeException("Active AI settings not found"));

	    return mapper.toResponse(entity);
	}
	
	@Override
	public void deactivateAllByOrganization(Long organizationId) {
		   List<AISettings> settingsList =
	                aiSettingsRepository.findByOrganizationId(organizationId);

	        settingsList.forEach(setting -> setting.setActive(false));

	        aiSettingsRepository.saveAll(settingsList);
		
	}

	@Override
	public void delete(Long id) {
		 aiSettingsRepository.deleteById(id);
		
	}

	@Override
	public List <AISettingsRequest> getActiveSettingsAll() {
		   return aiSettingsRepository.findAll()
		            .stream()
		            .map(mapper::toResponse)
		            .toList();
	}

	@Override
	public Optional<AISettingsRequest> findByUserName(String name) {
		
		   Optional<AISettings> settings =
		            aiSettingsRepository.findByModelName(name);

		    return settings.stream()
		            .findFirst()
		            .map(mapper::toResponse);
	}

}
