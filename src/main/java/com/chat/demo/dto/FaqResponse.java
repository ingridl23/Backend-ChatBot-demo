package com.chat.demo.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqResponse {

	private Long id;
	@NotBlank
	private String question;
	@NotBlank
    private String answer;
	@NotBlank
    private String  organizationName;
	@NotBlank
    private String areaName;
	@NotNull
    private Integer priority;
	@NotNull
    private Boolean isActive;
}


/**
 * 
 * {
   "id":12,
   "question":"¿Cómo subir habilitación?",
   "answer":"Ir a portal funcionario...",
   "organizationName":"Municipio Tres Arroyos",
   "areaName":"Comercio",
   "priority":4,
   "isActive":true
}
 * 
 */