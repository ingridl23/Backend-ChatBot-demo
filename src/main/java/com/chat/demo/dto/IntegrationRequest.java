package com.chat.demo.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
	@NotNull
	    private Long typeId;
	    @NotBlank
	    private String authType;
	    @NotBlank
	    private String baseUrl;
	    @NotBlank
	    private String apyKey;
	    @NotNull
	    private Boolean isActive;
	    @NotNull
	    private Long organizationId;
}
