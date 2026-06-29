package com.chat.demo.mapper;

import org.springframework.stereotype.Component;
import com.chat.demo.dto.AISettingsRequest;
import com.chat.demo.model.AISettings;



@Component
public class AISettingsMapper {

    public AISettings toEntity(AISettingsRequest dto) {

        return AISettings.builder()
                .modelName(dto.getModelName())
                .temperature(dto.getTemperature())
                .systemPrompt(dto.getSystemPrompt())
                .maxTokens(dto.getMaxTokens())
                .provider(dto.getProvider())
                .active(dto.getActive())
                .build();
    }

    public AISettingsRequest toResponse(AISettings entity) {

        AISettingsRequest dto = new AISettingsRequest();

        dto.setModelName(entity.getModelName());
        dto.setTemperature(entity.getTemperature());
        dto.setSystemPrompt(entity.getSystemPrompt());
        dto.setMaxTokens(entity.getMaxTokens());
        dto.setProvider(entity.getProvider());
        dto.setActive(entity.getActive());

        return dto;
    }
}
