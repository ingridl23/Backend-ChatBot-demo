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
public class SystemLinkRequest {
	@NotBlank
	    private String name;
	@NotBlank
	    private String url;
	@NotBlank
	    private String description;
	@NotNull
	    private Boolean isActive;
	@NotNull
	    private Long organizationId;
	@NotNull
	    private Long areaId;
}
