package com.chat.demo.dto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConversationRequest {
    private Long Id;
	    private String title;
	 
	    private Boolean status;

	    private Long userId;

	    private LocalDateTime createdAt;

	    private List<MessageRequest> messages;
	    
	    
	
	
}
