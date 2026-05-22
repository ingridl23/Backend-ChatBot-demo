package com.chat.demo.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqRequest {
	
	@NotBlank
	private String question;

	@NotBlank
    private String answer;
	@NotNull
    private Long organizationId;
	@NotNull
    private Long areaId;
    @NotBlank
    private Integer priority;
    @NotNull
    private Boolean isActive;
}


/**
 * JSON real de respuesta
 * {
   "question":"¿Cómo generar ticket en Notions?",
   "answer":"Ingresar al menú soporte > nuevo ticket",
   "organizationId":1,
   "areaId":3,
   "priority":5,
   "isActive":true
}
 */
