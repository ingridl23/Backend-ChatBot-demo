package com.chat.demo.controller.rag;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.demo.model.Integration;
import com.chat.demo.service.rag.IntegrationService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RestController
@Getter
@Setter
@RequestMapping("/api/integrations")
@RequiredArgsConstructor
public class IntegrationController {

	private final IntegrationService integraServ;
	
	
	// crear o guardar una integracion
	
	  @PostMapping
	  @PreAuthorize("hasAnyRole('ADMIN')")
	  public Integration integrationSave(@RequestBody Integration integration) {
		  return integraServ.save(integration);
	  }
	  
	  // modificar una integracion
	  
	  @PutMapping("/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN')")
	  public Integration integrationUpdate(@PathVariable Long id , @RequestBody Integration integra) {
		  return integraServ.update(id,integra);
	  }
	
	  
	  // encuentra una integracion mediante su id
	  
	  @GetMapping("/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN')")
	  public Optional <Integration> getIntegrationById(@PathVariable Long id){
		   return integraServ.findById(id);
	  }
	  
	  
	  
	  // encuentra una integracion mediante su nombre
	  
	  @GetMapping("/name/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN')")
	  public Optional<Integration> getIntegrationByName(@PathVariable String name){
		   return integraServ.findByName(name);
	  }
	  
	  // encuentra una integracion por su organizacion
	  
	  @GetMapping("/organization/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN')")
	  public List<Integration> getIntegrationByOrganization(@PathVariable Long id){
		   return integraServ.findByOrganization(id);
	  }
	  
	  
	  
	  // desactiva una integracion
	  @PatchMapping("/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN')")
	  public void  desactiveIntegration(@PathVariable Long id) {
		  integraServ.deactivate(id);
	  }
	  
	  // elimina una integracion
	  @DeleteMapping("/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN')")
	  public void deleteIntegration(@PathVariable Long Id) {
		  integraServ.delete(Id);
	  }
	  
	  
	  
}

