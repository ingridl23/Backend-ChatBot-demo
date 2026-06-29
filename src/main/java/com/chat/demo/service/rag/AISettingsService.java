package com.chat.demo.service.rag;
import java.util.List;
import java.util.Optional;

import com.chat.demo.dto.AISettingsRequest;
import com.chat.demo.model.AISettings;

/**
 * Uso futuro real
ChatService haría:

AISettings settings =
    aiSettingsService.getActiveSettings(orgId);
Luego:

settings.getProvider();
settings.getModelName();
settings.getTemperature();
y llamás:

OpenAI - Ollama - Claude

Así  chatbot queda configurable por cliente.

Decisión arquitectónica.
 */

public interface AISettingsService {
	
	
	AISettingsRequest save(AISettingsRequest settings);

	AISettingsRequest update(Long id, AISettingsRequest settings);

    Optional<AISettingsRequest> findById(Long id);

    Optional<AISettingsRequest> findByOrganizationId(Long organizationId);
    
    Optional<AISettingsRequest> findByModelName(String modelName);
    AISettingsRequest getActiveSettings(Long organizationId);
    
    List <AISettingsRequest> getActiveSettingsAll();

    void deactivateAllByOrganization(Long organizationId);

    void delete(Long id);
    
    
}
