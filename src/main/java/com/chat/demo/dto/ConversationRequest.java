package com.chat.demo.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConversationRequest {
	@NotNull
	private Long id;
	@NotBlank
	    private String title;
	@NotNull
	    private Boolean status;
	@NotNull
	    private Long userId;
	@NotNull
	    private List<MessageRequest> messages;
	    
	    
	
	
}
