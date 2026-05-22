package com.chat.demo.dto;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatResponse {
	@NotBlank
	 private String answer;
	@NotNull
	    private List<String> sources;
}
