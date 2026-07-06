package com.chat.demo.controller.rag;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.demo.dto.AISettingsRequest;
import com.chat.demo.model.User;
import com.chat.demo.service.auth.CustomUserDetails;
import com.chat.demo.service.rag.AISettingsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/aisettings")

@RequiredArgsConstructor
public class AISettingsController {

	private final AISettingsService aiServ;

	private User currentUser(Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		User user = userDetails.getUser();
		if (user.getOrganization() == null) {
			throw new RuntimeException("Authenticated user has no organization assigned");
		}
		return user;
	}

	// El id de una config solo se puede tocar/leer si pertenece a la organización del caller.
	private void assertOwnership(Long id, Long callerOrganizationId) {
		AISettingsRequest existing = aiServ.findById(id)
				.orElseThrow(() -> new RuntimeException("AI settings not found"));
		if (!callerOrganizationId.equals(existing.getOrganizationId())) {
			throw new AccessDeniedException("AI settings do not belong to the authenticated user's organization");
		}
	}

	//crear o guardar una configuracion
	@PostMapping
	  @PreAuthorize("hasAnyRole('ADMIN')")
	    public AISettingsRequest saveAI( @Valid @RequestBody AISettingsRequest request, Authentication authentication) {
		    User caller = currentUser(authentication);
		    // El organizationId nunca se toma del cliente: la configuración siempre
		    // queda en la organización del admin que la crea.
		    request.setOrganizationId(caller.getOrganization().getId());
		    return aiServ.save(request);
	    }

	// modificar una configuracion de conexion de IA

	 @PatchMapping("/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN')")
	 public AISettingsRequest updateSettingsAI ( @Valid @PathVariable Long id,@RequestBody AISettingsRequest request, Authentication authentication) {
		 User caller = currentUser(authentication);
		 assertOwnership(id, caller.getOrganization().getId());
		 request.setOrganizationId(caller.getOrganization().getId());
		 return aiServ.update(id,request);
	 }

	 // buscar una configuracion por id

	 @GetMapping("/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN','USER','AREA_ADMIN')")
	 public Optional<AISettingsRequest> foundSettingsAIById ( @Valid @PathVariable Long id, Authentication authentication) {
		 User caller = currentUser(authentication);
		 assertOwnership(id, caller.getOrganization().getId());
		 return aiServ.findById(id);
	 }

	// buscar una configuracion por organizacion

	 @GetMapping("/organization/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN','USER','AREA_ADMIN')")
	 public Optional <AISettingsRequest> foundSettingsAIByOrganizationId ( @Valid @PathVariable Long id, Authentication authentication) {
		 User caller = currentUser(authentication);
		 if (!id.equals(caller.getOrganization().getId())) {
			 throw new AccessDeniedException("Organization does not belong to the authenticated user");
		 }
		 return aiServ.findByOrganizationId(id);
	 }


	 // obtener la configuracion activa por defecto de la organizacion
	 @GetMapping("/active/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN','USER','AREA_ADMIN')")
	 public AISettingsRequest foundActiveSettingsAIById ( @Valid @PathVariable Long id, Authentication authentication) {
		 User caller = currentUser(authentication);
		 if (!id.equals(caller.getOrganization().getId())) {
			 throw new AccessDeniedException("Organization does not belong to the authenticated user");
		 }
		 return aiServ.getActiveSettings(id);
	 }

	 // obtener la configuracion activa para un area puntual (con fallback a la de la organizacion)
	 @GetMapping("/active/{id}/area/{areaId}")
	  @PreAuthorize("hasAnyRole('ADMIN','USER','AREA_ADMIN')")
	 public AISettingsRequest foundActiveSettingsAIByArea ( @PathVariable Long id, @PathVariable Long areaId, Authentication authentication) {
		 User caller = currentUser(authentication);
		 if (!id.equals(caller.getOrganization().getId())) {
			 throw new AccessDeniedException("Organization does not belong to the authenticated user");
		 }
		 return aiServ.getActiveSettings(id, areaId);
	 }

	 // obtener las configuraciones activas de la propia organizacion

	 @GetMapping
	  @PreAuthorize("hasAnyRole('ADMIN','USER','AREA_ADMIN')")
	 public List <AISettingsRequest> getActiveSettingsAll (Authentication authentication) {
		 User caller = currentUser(authentication);
		 return aiServ.getActiveSettingsAll()
				 .stream()
				 .filter(s -> caller.getOrganization().getId().equals(s.getOrganizationId()))
				 .toList();
	 }

	 // desactivar configuracion a una organizacion by id


	  @PatchMapping("deactive/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN')")
	  public void deactiveOrganizationById( @Valid @PathVariable Long id, Authentication authentication) {
		  User caller = currentUser(authentication);
		  if (!id.equals(caller.getOrganization().getId())) {
			  throw new AccessDeniedException("Organization does not belong to the authenticated user");
		  }
		  aiServ.deactivateAllByOrganization(id);
	  }


	//eliminar conversacion

	  @DeleteMapping("/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN')")
	  public void deleteConfiguration( @Valid @PathVariable Long id, Authentication authentication) {
		  User caller = currentUser(authentication);
		  assertOwnership(id, caller.getOrganization().getId());
		  aiServ.delete(id);
	  }

}
