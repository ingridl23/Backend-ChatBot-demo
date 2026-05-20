package com.chat.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AISettingsRequest {
	
	@NotBlank
	private String modelName;

    private Long organizationId;

    private Double temperature;
    
    
    @NotBlank
    private String systemPrompt;

    private Integer maxTokens;
    @NotBlank
    private String provider;

    private Boolean active;
}
