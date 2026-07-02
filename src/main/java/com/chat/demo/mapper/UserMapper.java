package com.chat.demo.mapper;

import org.springframework.stereotype.Component;

import com.chat.demo.dto.UserResponse;
import com.chat.demo.model.Role;
import com.chat.demo.model.User;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {

        UserResponse dto = new UserResponse();

        dto.setId(user.getId());
        dto.setUserName(user.getUserName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setEnabled(user.getEnabled());

        if (user.getOrganization() != null) {
            dto.setOrganizationId(user.getOrganization().getId());
            dto.setOrganizationName(user.getOrganization().getName());
        }

        if (user.getArea() != null) {
            dto.setAreaId(user.getArea().getId());
            dto.setAreaName(user.getArea().getName());
        }

        dto.setRoles(user.getRoles().stream().map(Role::getName).toList());

        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        return dto;
    }
}
