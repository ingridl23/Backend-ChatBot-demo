package com.chat.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaRequest {
    @NotBlank
    private String name;
    private String description;
}
