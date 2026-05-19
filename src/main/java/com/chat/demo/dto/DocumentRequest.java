package com.chat.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentRequest {

	private String title;

    private Long statusId;

    private Long organizationId;

    private Long areaId;

    private Long uploadedById;

    private String fileName;

    private String mimeType;

    private Long fileSize;
	    
	    
}
