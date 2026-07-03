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
	// Opcional: si se especifica, esta configuración solo aplica a esa área
	// (permite un tono/saludo distinto por área). Nulo = configuración por defecto de la organización.
	private Long areaId;
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
