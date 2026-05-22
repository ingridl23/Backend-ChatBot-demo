package com.chat.demo.controller.rag;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.demo.dto.OrganizationRequest;
import com.chat.demo.model.Organization;
import com.chat.demo.service.rag.OrganizationService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RestController
@Getter
@Setter
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

	private final OrganizationService orgServ;
	
	
	//crea o guarda ina organizacion
	
	@PostMapping
	  @PreAuthorize("hasAnyRole('ADMIN')")
	public OrganizationRequest saveOrganization(@RequestBody OrganizationRequest org) {
		 return orgServ.save(org);
	}
	
	// modifica una organizacion 
	
	@PutMapping
	  @PreAuthorize("hasAnyRole('ADMIN')")
	public OrganizationRequest updateOrganization(@PathVariable Long id ,@RequestBody OrganizationRequest org) {
		 return orgServ.update(id,org);
	}
	
	// encuentra una organizacion por su id
	
	@GetMapping("/{id}")
	 @PreAuthorize("hasAnyRole('ADMIN','USER')")
	public Optional<OrganizationRequest> getOrganizationById(@PathVariable Long id){
		return orgServ.findById(id);
	}
	
	// encuentra una organizacion por su nombre
	
	@GetMapping("/name/{id}")
	 @PreAuthorize("hasAnyRole('ADMIN','USER')")
	public Optional<OrganizationRequest> getOrganizationByName(@PathVariable String name){
		return orgServ.findByName(name);
	}
	
	// encuentra una organizacion por su dominio
	
	@GetMapping("/domain/{id}")
	 @PreAuthorize("hasAnyRole('ADMIN','USER')")
	public Optional<OrganizationRequest> getOrganizationByDomain(@PathVariable String domain){
		return orgServ.findByDomain(domain);
	}
	
	//Lista todas las organizaciones
	
	@GetMapping("/")
	 @PreAuthorize("hasAnyRole('ADMIN','USER')")
		public List<OrganizationRequest> getOrganizations(){
			return orgServ.findAll();
		}
	
	// elimina una organizacion
	
	@DeleteMapping("/{id}")
	 @PreAuthorize("hasAnyRole('ADMIN')")
	public void deleteOrganization(@PathVariable Long id){
		orgServ.delete(id);
	}
	
}
