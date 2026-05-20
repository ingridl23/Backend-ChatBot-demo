package com.chat.demo.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRequest {
	@NotBlank
	   private String question;
	    private Long conversationId;
}
