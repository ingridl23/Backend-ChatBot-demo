package com.chat.demo.dto;

import java.util.List;

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
    // Vacío u omitido = documento global (visible para toda la organización)
    private List<Long> areaIds;
	@NotNull
    private Long uploadedById;
    @NotBlank
    private String fileName;
    @NotBlank
    private String mimeType;
    @NotNull
    private Long fileSize;
    @NotNull
    private String filePath;

}
