package com.chat.demo.dto;

import java.util.Set;
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
public class UserRequest {
	@NotBlank
	private String userName;
	@NotBlank
    private String lastName;
	@NotBlank
	private String email;
	@NotBlank
	private String password;
	@NotNull
	private Boolean enabled ;
	// Lo sobreescribe el controller desde el JWT del admin autenticado; no se toma del cliente.
	private Long organizationId;
	@NotNull
	private Long areaId;
	@NotNull
	private Set<Long> rolesId;
	
	
	}