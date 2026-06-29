package com.chat.demo.mapper;

import org.springframework.stereotype.Component;
import com.chat.demo.dto.ConversationRequest;

import com.chat.demo.model.Conversation;

@Component
public class ConversationMapper {
	
	  public Conversation toEntity(ConversationRequest dto) {

	        return Conversation.builder()
	                .title(dto.getTitle())
	                .status(dto.getStatus())
	                .build();
	    }
	
	

	public ConversationRequest toResponse(Conversation con) {
	    ConversationRequest dto = new ConversationRequest();
	    dto.setId(con.getId());
	    dto.setTitle(con.getTitle());
	    dto.setStatus(con.getStatus());
	    
	    if (con.getUser() != null) {
	        dto.setUserId(con.getUser().getId());
	    }

	    return dto;
	    
	    
	}
}
