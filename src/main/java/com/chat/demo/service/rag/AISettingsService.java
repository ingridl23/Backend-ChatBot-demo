package com.chat.demo.service.rag;
import java.util.List;
import java.util.Optional;
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
	AISettings save(AISettings settings);

    AISettings update(Long id, AISettings settings);

    Optional<AISettings> findById(Long id);

    Optional<AISettings> findByOrganizationId(Long organizationId);

    AISettings getActiveSettings(Long organizationId);
    
    List <AISettings> getActiveSettingsAll();

    void deactivateAllByOrganization(Long organizationId);

    void delete(Long id);
}
