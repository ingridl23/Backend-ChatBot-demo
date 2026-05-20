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
public class IntegrationRequest {
	@NotBlank
	    private String name;
	    private Long typeId;
	    @NotBlank
	    private String authType;
	    @NotBlank
	    private String baseUrl;
	    @NotBlank
	    private String apyKey;
	    private Boolean isActive;
	    private Long organizationId;
}
