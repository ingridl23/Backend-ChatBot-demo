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

import com.chat.demo.model.AISettings;

import com.chat.demo.service.rag.AISettingsService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RestController
@Getter
@Setter
@RequestMapping("/api/aisettings")

@RequiredArgsConstructor
public class AISettingsController {

	private final AISettingsService AIserv;
	
	//crear o guardar una configuracion
	@PostMapping
	  @PreAuthorize("hasAnyRole('ADMIN')")
	    public AISettings saveAI(@RequestBody AISettings request) {
	        return AIserv.save(request);
	    }
	
	// modificar una configuracion de conexion de IA 
	
	 @PatchMapping("/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN')")
	 public AISettings updateSettingsAI (@PathVariable Long id,@RequestBody AISettings request) {
		 return AIserv.update(id,request);
	 }
	
	 // buscar una configuracion por id
	 
	 @GetMapping("/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN','USER')")
	 public AISettings foundSettingsAIById (@PathVariable Long id,@RequestBody AISettings request) {
		 return AIserv.update(id,request);
	 }
	 
	// buscar una configuracion por organizacion
	 
	 @GetMapping("/organization/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN','USER')")
	 public Optional <AISettings> foundSettingsAIByOrganizationId (@PathVariable Long id) {
		 return AIserv.findByOrganizationId(id);
	 }
	 
	 
	 // obtener la configuracion activa
	 @GetMapping("/active/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN','USER')")
	 public AISettings foundActiveSettingsAIById (@PathVariable Long id) {
		 return AIserv.getActiveSettings(id);
	 }
	 
	 // obtener las configuraciones 
	 
	 @GetMapping("/")
	  @PreAuthorize("hasAnyRole('ADMIN','USER')")
	 public List <AISettings> getActiveSettingsAll () {
		 return AIserv.getActiveSettingsAll();
	 }
	 
	 // desactivar configuracion a una organizacion by id
	 
	 
	  @PatchMapping("deactive/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN')")
	  public void deactiveOrganizationById(@PathVariable Long id) {
		  AIserv.deactivateAllByOrganization(id);
	  }
	  
	  
	//eliminar conversacion
		 
	  @DeleteMapping("/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN')")
	  public void deleteConfiguration(@PathVariable Long id) {
		  AIserv.delete(id);
	  }
	 
}
