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
	@NotBlank
	    private String logoUrl;
	@NotBlank
	    private String primaryColor;
	@NotBlank
	    private String secondaryColor;
	@NotBlank
	    private String faviconUrl;
	@NotBlank
        private String domain;
	@NotBlank
	    private String supportEmail;
	    
    
}
