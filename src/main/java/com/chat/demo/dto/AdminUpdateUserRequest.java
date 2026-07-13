package com.chat.demo.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateUserRequest {
    @NotBlank
    private String userName;
    @NotBlank
    private String email;
    // Opcional: si viene vacío/null, UserServiceImpl.updateUser no toca la contraseña actual.
    private String password;
    // Opcionales: si vienen null, UserServiceImpl.updateUser no toca área ni roles actuales.
    private Long areaId;
    private Set<Long> rolesId;
}
