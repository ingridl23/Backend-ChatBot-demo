package com.chat.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRequest {

    @NotBlank
    private String question;

    @NotNull
    private Long conversationId;

    // Estos campos los sobreescribe el controller desde el token JWT,
    // no se toman del body enviado por el cliente.
    private Long organizationId;
    private Long userId;
    private Long areaId;
}
