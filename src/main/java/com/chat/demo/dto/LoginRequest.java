package com.chat.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private Boolean enabled;

}
