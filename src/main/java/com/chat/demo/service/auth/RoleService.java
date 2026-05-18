package com.chat.demo.service.auth;

import java.util.List;
import java.util.Optional;

import com.chat.demo.dto.RoleRequest;
import com.chat.demo.model.Role;

;

public interface RoleService {

	Role createRole(RoleRequest request);

    List<Role> getAllRoles();

    Optional<Role> getRolesById(Long id);

    void deleteRole(Long id);
    void updateRole(Long id,String nombre);
}
