package com.chat.demo.controller.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {

	private final RoleServiceImpl roleService;

   
	 @PostMapping
	   
	    public Role createRole(@RequestBody RoleRequest request) {
	        return roleService.createRole(request);
	    }

	    @GetMapping
	 
	    public List<Role> getAllRoles() {
	        return roleService.getAllRoles();
	    }

	    @GetMapping("/{id}")
	  
	    public Optional<Role> getRolById(@PathVariable Long id) {
	        return roleService.getRolesById(id);
	    }

	    @DeleteMapping("/{id}")
	  
	    public void deleteRole(@PathVariable Long id) {
	    	roleService.deleteRole(id);
	    }
	    
	    @PatchMapping("/{id}")
		  @PreAuthorize("hasRole('ADMIN')")
	      public void updateRole(@PathVariable Long id,String nombre) {
		   roleService.updateRole(id,nombre);
	   }
}
