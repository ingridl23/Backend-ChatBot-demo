package com.chat.demo.controller.auth;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.demo.dto.PermissionRequest;
import com.chat.demo.model.Permission;
import com.chat.demo.service.auth.impl.PermissionServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class PermissionController {

	private final PermissionServiceImpl permissionService;

    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public Permission createPermission(@RequestBody PermissionRequest request) {
        return permissionService.createPermission(request);
    }

    @GetMapping
   // @PreAuthorize("hasRole('ADMIN')")
    public List<Permission> getAllPermissions() {
        return permissionService.getAllPermissions();
    }

    @GetMapping("/{id}")
   // @PreAuthorize("hasRole('ADMIN')")
    public Permission getPermissionById(@PathVariable Long id) {
        return permissionService.getPermissionById(id);
    }

    @DeleteMapping("/{id}")
   // @PreAuthorize("hasRole('ADMIN')")
    public void deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
    }
	
    @PatchMapping("/{id}")
	  @PreAuthorize("hasRole('ADMIN')")
    public void updatePermission(@PathVariable Long id,String nombre) {
	   permissionService.updatePermission(id,nombre);
 }
	
	
	
	
}
