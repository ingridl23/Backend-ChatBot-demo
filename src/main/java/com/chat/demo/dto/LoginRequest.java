package com.chat.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	//private Long id;
	@NotBlank
    private String userName;
	@NotBlank
    private String lastName;
    @Email
    private String email;
    
    @NotBlank
    private String password;
    private Boolean enabled;

}
