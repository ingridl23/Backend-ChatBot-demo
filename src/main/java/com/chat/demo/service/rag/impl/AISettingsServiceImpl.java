package com.chat.demo.service.rag.impl;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.chat.demo.model.AISettings;
import com.chat.demo.repository.AISettingsRepository;
import com.chat.demo.service.rag.AISettingsService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AISettingsServiceImpl implements AISettingsService{

	private final AISettingsRepository aiSettingsRepository;
	@Override
	public AISettings save(AISettings settings) {
		  settings.setCreatedAt(LocalDateTime.now());
	        settings.setUpdatedAt(LocalDateTime.now());

	        return aiSettingsRepository.save(settings);
	}

	@Override
	public AISettings update(Long id, AISettings settings) {
		
		  AISettings existing = aiSettingsRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("AI settings not found"));

	        existing.setModelName(settings.getModelName());
	        existing.setProvider(settings.getProvider());
	        existing.setTemperature(settings.getTemperature());
	        existing.setSystemPrompt(settings.getSystemPrompt());
	        existing.setMaxTokens(settings.getMaxTokens());
	        existing.setActive(settings.getActive());
	        existing.setUpdatedAt(LocalDateTime.now());

	        return aiSettingsRepository.save(existing);
	}

	@Override
	public Optional<AISettings> findById(Long id) {
		  return aiSettingsRepository.findById(id);
	}

	@Override
	public Optional<AISettings> findByOrganizationId(Long organizationId) {
		 List<AISettings> settings = aiSettingsRepository.findByOrganizationId(organizationId);

	        return settings.stream().findFirst();
	}

	@Override
	public AISettings getActiveSettings(Long organizationId) {
		return aiSettingsRepository
                .findByOrganizationIdAndActiveTrue(organizationId)
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
	public List <AISettings> getActiveSettingsAll() {
		return aiSettingsRepository.findAll();
	}

}
