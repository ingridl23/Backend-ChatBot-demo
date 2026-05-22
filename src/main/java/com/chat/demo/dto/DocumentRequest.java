package com.chat.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentRequest {
	@NotBlank
	private String title;
	@NotNull
    private Long statusId;
	@NotNull
    private Long organizationId;
	@NotNull
    private Long areaId;
	@NotNull
    private Long uploadedById;
    @NotBlank
    private String fileName;
    @NotBlank
    private String mimeType;
    @NotNull
    private Long fileSize;
	    
	    
}
