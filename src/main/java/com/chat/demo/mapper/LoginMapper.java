package com.chat.demo.mapper;

import org.springframework.stereotype.Component;
import com.chat.demo.dto.LoginRequest;
import com.chat.demo.model.User;

@Component
public class LoginMapper {

	 public User toEntity(LoginRequest dto) {

	        return User.builder()
	                .userName(dto.getUserName())
	                .lastName(dto.getLastName())
	                .email(dto.getEmail())
	                .password(dto.getPassword())
	                .enabled(dto.getEnabled())
	                .build();
	    }

	    public LoginRequest toResponse(User entity) {

	        LoginRequest dto = new LoginRequest();

	        dto.setUserName(entity.getUserName());
	        dto.setLastName(entity.getLastName());
	        dto.setEmail(entity.getEmail());
	        dto.setPassword(entity.getPassword());
	        dto.setEnabled(entity.getEnabled());
	      

	        return dto;
	    }
}
