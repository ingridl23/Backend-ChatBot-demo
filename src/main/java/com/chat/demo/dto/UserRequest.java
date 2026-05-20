package com.chat.demo.dto;

import java.util.Set;
import jakarta.validation.constraints.NotBlank;
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
	private Boolean enabled ;
	private Long organizationId;
	private Long areaId;
	private Set<Long> rolesId;
	
	
	}