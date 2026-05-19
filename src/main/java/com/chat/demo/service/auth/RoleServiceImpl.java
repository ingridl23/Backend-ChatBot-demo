package com.chat.demo.service.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chat.demo.dto.RoleRequest;
import com.chat.demo.model.Role;
import com.chat.demo.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class RoleServiceImpl  implements RoleService{
	
	private final RoleRepository roleRepository;

	@Override
	public Role createRole(RoleRequest request) {
		 Role rol = Role.builder()
	                .name(request.getRolename())
	                .build(); 
	   
	        return roleRepository.save(rol);
	}

	@Override
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public Optional<Role> getRolesById(Long id) {
		return roleRepository.findById(id);
		
	}

	@Override
	public void deleteRole(Long id) {
			 roleRepository.deleteById(id);
		
	}

	@Override
	public void updateRole(Long id, String nombre) {
		   Role role = roleRepository.findById(id)
			        .orElseThrow(() -> new RuntimeException("Rol not found"));
	
        	    role.setName(nombre);

        	    roleRepository.save(role);
        	    
	
	
	}}