package com.chat.demo.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRequest {
	@NotBlank
	   private String question;
	@NotNull
	    private Long conversationId;
	@NotNull
    	private Long organizationId;
	@NotNull
	    private Long userId;
}
