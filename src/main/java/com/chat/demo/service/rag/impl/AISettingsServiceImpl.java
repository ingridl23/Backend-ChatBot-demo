package com.chat.demo.service.rag.impl;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.chat.demo.dto.AISettingsRequest;
import com.chat.demo.mapper.AISettingsMapper;
import com.chat.demo.model.AISettings;
import com.chat.demo.model.Area;
import com.chat.demo.model.Organization;

import com.chat.demo.repository.AISettingsRepository;
import com.chat.demo.repository.AreaRepository;
import com.chat.demo.repository.OrganizationRepository;
import com.chat.demo.service.rag.AISettingsService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AISettingsServiceImpl implements AISettingsService{

	private final AISettingsRepository aiSettingsRepository;
	private final AISettingsMapper mapper;
	private final OrganizationRepository organizationRepository;
	private final AreaRepository areaRepository;

	private Area resolveArea(Long areaId, Long organizationId) {
		if (areaId == null) {
			return null;
		}
		Area area = areaRepository.findById(areaId)
				.orElseThrow(() -> new RuntimeException("Area not found"));
		if (!area.getOrganization().getId().equals(organizationId)) {
			throw new RuntimeException("Area does not belong to the specified organization");
		}
		return area;
	}

	// Solo puede haber una config activa por combinación organización+área: si se crea
	// una nueva activa, se desactiva la anterior para esa misma área (o el default de la
	// organización si area == null), así el front puede llamar a save() repetidamente
	// sin generar duplicados que rompan getActiveSettings().
	private void deactivatePreviousActive(Long organizationId, Area area, Long excludeId) {
		Optional<AISettings> previous = area == null
				? aiSettingsRepository.findByOrganizationIdAndAreaIsNullAndActiveTrue(organizationId)
				: aiSettingsRepository.findByOrganizationIdAndAreaIdAndActiveTrue(organizationId, area.getId());

		previous.filter(existing -> !existing.getId().equals(excludeId))
				.ifPresent(existing -> {
					existing.setActive(false);
					aiSettingsRepository.save(existing);
				});
	}

	@Override
	public AISettingsRequest save(AISettingsRequest settings) {

		Organization organization = organizationRepository.findById(settings.getOrganizationId())
				.orElseThrow(() -> new RuntimeException("Organization not found"));
		Area area = resolveArea(settings.getAreaId(), organization.getId());

		if (Boolean.TRUE.equals(settings.getActive())) {
			deactivatePreviousActive(organization.getId(), area, null);
		}

	       AISettings entity =
	                mapper.toEntity(settings);

	        entity.setOrganization(organization);
	        entity.setArea(area);
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
		entity.setArea(resolveArea(settings.getAreaId(), entity.getOrganization().getId()));

		if (Boolean.TRUE.equals(settings.getActive())) {
			deactivatePreviousActive(entity.getOrganization().getId(), entity.getArea(), entity.getId());
		}
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
		return getActiveSettings(organizationId, null);
	}

	@Override
	public AISettingsRequest getActiveSettings(Long organizationId, Long areaId) {
		Optional<AISettings> entity = Optional.empty();

		if (areaId != null) {
			entity = aiSettingsRepository.findByOrganizationIdAndAreaIdAndActiveTrue(organizationId, areaId);
		}
		if (entity.isEmpty()) {
			entity = aiSettingsRepository.findByOrganizationIdAndAreaIsNullAndActiveTrue(organizationId);
		}

		return entity.map(mapper::toResponse)
				.orElseThrow(() -> new RuntimeException("Active AI settings not found"));
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
	public List<AISettingsRequest> getActiveSettingsAll() {
		return aiSettingsRepository.findAllByActiveTrue()
		        .stream()
		        .map(mapper::toResponse)
		        .toList();
	}

	@Override
	public Optional<AISettingsRequest> findByModelName(String modelName) {
		return aiSettingsRepository.findByModelName(modelName)
		        .map(mapper::toResponse);
	}

}
