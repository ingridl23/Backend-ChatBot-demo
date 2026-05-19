package com.chat.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AISettingsRequest {
	private String modelName;

    private Long organizationId;

    private Double temperature;

    private String systemPrompt;

    private Integer maxTokens;

    private String provider;

    private Boolean active;
}
