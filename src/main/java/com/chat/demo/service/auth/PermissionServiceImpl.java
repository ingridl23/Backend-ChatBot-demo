package com.chat.demo.service.auth;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chat.demo.dto.PermissionRequest;
import com.chat.demo.model.Permission;
import com.chat.demo.repository.PermissionRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService{

	private final PermissionRepository permissionRepository;
	@Override
	public Permission createPermission(PermissionRequest request) {
		 Permission permission = Permission.builder()
	                .name(request.getName())
	                .build();

	        return permissionRepository.save(permission);
	}

	@Override
	public List<Permission> getAllPermissions() {
		 return permissionRepository.findAll();
	}

	@Override
	public Permission getPermissionById(Long id) {
		 return permissionRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Permission not found"));
	}

	@Override
	public void deletePermission(Long id) {
		permissionRepository.deleteById(id);
		
	}

	@Override
	public void updatePermission(Long id, String nombre) {
		  Permission permission = permissionRepository.findById(id)
			        .orElseThrow(() -> new RuntimeException("Permission no encontrado"));

			    permission.setName(nombre);

			    permissionRepository.save(permission);
		
	}

}
