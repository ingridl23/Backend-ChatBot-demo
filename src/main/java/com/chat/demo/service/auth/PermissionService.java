package com.chat.demo.service.auth;
import java.util.List;
import com.chat.demo.dto.PermissionRequest;
import com.chat.demo.model.Permission;



public interface PermissionService {
	Permission createPermission(PermissionRequest request);

    List<Permission> getAllPermissions();

    Permission getPermissionById(Long id);

    void deletePermission(Long id);
    void updatePermission(Long id,String nombre);
}
