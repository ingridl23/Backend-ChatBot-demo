package com.chat.demo.dto;

import java.util.Set;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequest {
	@NotNull
	private Long id;
	@NotBlank
	private String rolename;
	@NotNull
	private Set <PermissionRequest> permissions;
}