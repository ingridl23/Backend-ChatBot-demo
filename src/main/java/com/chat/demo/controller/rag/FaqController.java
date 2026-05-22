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

import com.chat.demo.dto.FaqRequest;
import com.chat.demo.model.Faq;
import com.chat.demo.service.rag.FaqService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RestController
@Getter
@Setter
@RequestMapping("/api/faqs")
@RequiredArgsConstructor
public class FaqController {
	
	private final FaqService faqServ;
	
	
	// crear o guardar un faq
	@PostMapping
	  @PreAuthorize("hasAnyRole('ADMIN')")
	public FaqRequest faqSave(@RequestBody FaqRequest request) {
		return faqServ.save(request);
	}
	
	
	// modificar un faq 
	@PutMapping("/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN')")
	public FaqRequest faqUpdate(@PathVariable Long id , @RequestBody FaqRequest request) {
		
		 return faqServ.update(id, request);
	}
	
	//Buscar un faq mediante su id
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public Optional<FaqRequest> faqFoundById(@PathVariable Long id){
		return faqServ.findById(id);
	}

	// buscar un faq a partir de una pregunta
	
	
	@GetMapping("/question/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public Optional<FaqRequest> faqFoundByQuestionId(@PathVariable String question){
		return faqServ.findByQuestion(question);
	}
	
	// buscar y listar faqs por organizacion
	
	@GetMapping("/organization/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<FaqRequest> faqFoundByOrganizationId(@PathVariable Long organizationId){
		return faqServ.findByOrganization(organizationId);
	}
	
	// buscar y lisstar faqs por area
	
	@GetMapping("/area/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<FaqRequest> faqFoundByAreaId(@PathVariable Long Id){
		return faqServ.findByArea(Id);
	}
	
	
	// buscar y listar faqs activos
	
	@GetMapping("/")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<FaqRequest> faqListActive(){
		return faqServ.findActive();
	}
	
	//eliminar un faq
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public void faqListActive(@PathVariable Long id){
		 faqServ.delete(id);
	}
	
	
}