package com.chat.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentRequest {
	@NotBlank
	private String title;

    private Long statusId;

    private Long organizationId;

    private Long areaId;

    private Long uploadedById;
    @NotBlank
    private String fileName;
    @NotBlank
    private String mimeType;

    private Long fileSize;
	    
	    
}
