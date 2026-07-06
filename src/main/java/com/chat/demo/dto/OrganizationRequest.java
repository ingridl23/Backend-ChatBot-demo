package com.chat.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationRequest {
	@NotBlank
	    private String name;
	    // Gestionados exclusivamente por uploadLogo/uploadFavicon (POST .../logo, .../favicon):
	    // este campo solo se lee en las respuestas, nunca se exige ni se aplica en save/update.
	    private String logoUrl;
	@NotBlank
	    private String primaryColor;
	@NotBlank
	    private String secondaryColor;
	    private String faviconUrl;
	@NotBlank
        private String domain;
	@NotBlank
	    private String supportEmail;
	    
    
}
