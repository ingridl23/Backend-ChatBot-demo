package com.chat.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	private Long id;
    private String username;
    private String password;

}
