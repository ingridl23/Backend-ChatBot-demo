package com.chat.demo.controller.rag;

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

import com.chat.demo.dto.AISettingsRequest;


import com.chat.demo.service.rag.AISettingsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/aisettings")

@RequiredArgsConstructor
public class AISettingsController {

	private final AISettingsService aiServ;
	
	//crear o guardar una configuracion
	@PostMapping
	  @PreAuthorize("hasAnyRole('ADMIN')")
	    public AISettingsRequest saveAI( @Valid @RequestBody AISettingsRequest request) {
	        return aiServ.save(request);
	    }
	
	// modificar una configuracion de conexion de IA 
	
	 @PatchMapping("/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN')")
	 public AISettingsRequest updateSettingsAI ( @Valid @PathVariable Long id,@RequestBody AISettingsRequest request) {
		 return aiServ.update(id,request);
	 }
	
	 // buscar una configuracion por id
	 
	 @GetMapping("/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN','USER')")
	 public Optional<AISettingsRequest> foundSettingsAIById ( @Valid @PathVariable Long id) {
		 return aiServ.findById(id);
	 }
	 
	// buscar una configuracion por organizacion
	 
	 @GetMapping("/organization/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN','USER')")
	 public Optional <AISettingsRequest> foundSettingsAIByOrganizationId ( @Valid @PathVariable Long id) {
		 return aiServ.findByOrganizationId(id);
	 }
	 
	 
	 // obtener la configuracion activa
	 @GetMapping("/active/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN','USER')")
	 public AISettingsRequest foundActiveSettingsAIById ( @Valid @PathVariable Long id) {
		 return aiServ.getActiveSettings(id);
	 }
	 
	 // obtener las configuraciones 
	 
	 @GetMapping
	  @PreAuthorize("hasAnyRole('ADMIN','USER')")
	 public List <AISettingsRequest> getActiveSettingsAll () {
		 return aiServ.getActiveSettingsAll();
	 }
	 
	 // desactivar configuracion a una organizacion by id
	 
	 
	  @PatchMapping("deactive/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN')")
	  public void deactiveOrganizationById( @Valid @PathVariable Long id) {
		  aiServ.deactivateAllByOrganization(id);
	  }
	  
	  
	//eliminar conversacion
		 
	  @DeleteMapping("/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN')")
	  public void deleteConfiguration( @Valid @PathVariable Long id) {
		  aiServ.delete(id);
	  }
	 
}
