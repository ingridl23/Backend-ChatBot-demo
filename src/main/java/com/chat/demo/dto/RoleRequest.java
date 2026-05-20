package com.chat.demo.dto;

import java.util.Set;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequest {
	private Long id;
	@NotBlank
	private String rolename;
	private Set <PermissionRequest> permissions;
}