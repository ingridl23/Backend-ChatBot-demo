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

    // Configuración activa para un área puntual, con fallback a la de la organización
    // si el área no tiene una propia (areaId puede ser null para pedir directamente la de la org).
    AISettingsRequest getActiveSettings(Long organizationId, Long areaId);
    
    List <AISettingsRequest> getActiveSettingsAll();

    void deactivateAllByOrganization(Long organizationId);

    void delete(Long id);
    
    
}
