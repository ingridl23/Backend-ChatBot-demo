package com.chat.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentResponse {

	
	 private Long id;

	    private String title;

	    private String fileName;

	    private String mimeType;

	    private Long fileSize;

	    private Long statusId;

	    private Long organizationId;

	    private List<Long> areaIds;

	    private Long uploadedById;

	    private LocalDateTime uploadedAt;

	    private LocalDateTime createdAt;
}
