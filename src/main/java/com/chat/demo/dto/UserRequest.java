package com.chat.demo.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
	private Long id;
	private String username;
	private String password;
	private String email;
	  private Set<String> roles;
	}