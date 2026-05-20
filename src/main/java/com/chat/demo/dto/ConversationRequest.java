package com.chat.demo.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConversationRequest {
      
	@NotBlank
	    private String title;
	 
	    private Boolean status;

	    private Long userId;

	    private List<MessageRequest> messages;
	    
	    
	
	
}
