package com.chat.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AISettingsRequest {
	
	@NotBlank
	private String modelName;
	@NotNull
    private Long organizationId;
	@NotNull
    private Double temperature;
    
    
    @NotBlank
    private String systemPrompt;
    @NotNull 
    private Integer maxTokens;
    @NotBlank
    private String provider;
    @NotNull
    private Boolean active;
}
