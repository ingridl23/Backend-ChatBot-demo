package com.chat.demo.controller.rag;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.demo.dto.FaqRequest;
import com.chat.demo.dto.FaqResponse;
import com.chat.demo.model.Area;
import com.chat.demo.model.User;
import com.chat.demo.service.auth.CustomUserDetails;
import com.chat.demo.service.rag.AreaService;
import com.chat.demo.service.rag.FaqService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/faqs")
@RequiredArgsConstructor
public class FaqController {

	private final FaqService faqServ;
	private final AreaService areaServ;

	private User currentUser(Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		User user = userDetails.getUser();
		if (user.getOrganization() == null) {
			throw new RuntimeException("Authenticated user has no organization assigned");
		}
		return user;
	}


	// crear o guardar un faq
	@PostMapping
	  @PreAuthorize("hasAnyRole('ADMIN')")
	public FaqResponse faqSave(@RequestBody FaqRequest request) {
		return faqServ.save(request);
	}


	// modificar un faq
	@PutMapping("/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN')")
	public FaqResponse faqUpdate(@PathVariable Long id , @RequestBody FaqRequest request) {

		 return faqServ.update(id, request);
	}

	//Buscar un faq mediante su id

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public Optional<FaqResponse> faqFoundById(@PathVariable Long id){
		return faqServ.findById(id);
	}

	// buscar un faq a partir de una pregunta


	@GetMapping("/question/{question}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public Optional<FaqResponse> faqFoundByQuestionId(@PathVariable String question){
		return faqServ.findByQuestion(question);
	}

	// buscar y listar faqs por organizacion

	@GetMapping("/organization/{organizationId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<FaqResponse> faqFoundByOrganizationId(@PathVariable Long organizationId, Authentication authentication){
		User caller = currentUser(authentication);
		// El organizationId de la URL es un dato del cliente y puede modificarse:
		// nunca se confía en él sin verificar que sea el de la propia organización.
		if (!organizationId.equals(caller.getOrganization().getId())) {
			throw new AccessDeniedException("Organization does not belong to the authenticated user");
		}
		return faqServ.findByOrganization(organizationId);
	}

	// buscar y lisstar faqs por area

	@GetMapping("/area/{areaId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<FaqResponse> faqFoundByAreaId(@PathVariable Long areaId, Authentication authentication){
		User caller = currentUser(authentication);
		Area area = areaServ.findById(areaId)
				.orElseThrow(() -> new RuntimeException("Area not found"));
		// Mismo criterio: el areaId viene del cliente, se valida contra la organización real.
		if (!area.getOrganization().getId().equals(caller.getOrganization().getId())) {
			throw new AccessDeniedException("Area does not belong to the authenticated user's organization");
		}
		return faqServ.findByArea(areaId);
	}
	
	
	// buscar y listar faqs activos
	
	@GetMapping("/")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<FaqResponse> faqListActive(){
		return faqServ.findActive();
	}
	
	//eliminar un faq
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public void faqListActive(@PathVariable Long id){
		 faqServ.delete(id);
	}
	
	
}