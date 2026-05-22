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

import com.chat.demo.dto.DocumentRequest;
import com.chat.demo.model.Document;

import com.chat.demo.service.rag.DocumentService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RestController
@Getter
@Setter
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

	private final  DocumentService docServ;
	
	
	//crear o guardar una configuracion
		  @PostMapping
		  @PreAuthorize("hasAnyRole('ADMIN')")
		    public DocumentRequest documentSave(@RequestBody DocumentRequest request) {
		        return docServ.save(request);
		    }
		  
		  
		
		// modificar una documentacion cargada
		
		 @PutMapping("/{id}")
		  @PreAuthorize("hasAnyRole('ADMIN')")
		 public DocumentRequest documentUpdate (@PathVariable Long id,@RequestBody DocumentRequest request) {
			 return docServ.update(id,request);
		 }
		
		 // buscar una documentacion por id
		 
		 @GetMapping("/{id}")
		  @PreAuthorize("hasAnyRole('ADMIN','USER')")
		 public Optional<DocumentRequest> foundDocumentationById (@PathVariable Long id) {
			 return docServ.findById(id);
		 }
		 
		 
		// buscar una documentacion por su titulo
		 
		 @GetMapping("/title/{id}")
		  @PreAuthorize("hasAnyRole('ADMIN','USER')")
		 public Optional<DocumentRequest> foundDocumentationByTitle (@PathVariable String title) {
			 return docServ.findByTitle(title);
		 }
		 
		 
		 // obtener la documentacion por organizacion
		  @GetMapping("/organization/{id}")
		  @PreAuthorize("hasAnyRole('ADMIN','USER')")
		 public Optional <DocumentRequest> foundDocumentationByOrganization (@PathVariable Long id) {
			 return docServ.findByOrganization(id);
		 }
		 
		 
		 // obtener documentacion por area (oficinas o especialidades)
		 
		  @GetMapping("/area/{id}")
		  @PreAuthorize("hasAnyRole('ADMIN','USER')")
		 public Optional <DocumentRequest> foundDocumentationByArea (@PathVariable Long id) {
			 return docServ.findByArea(id);
		 }
		 
		 
		 // obtener documentacion cargada por un usuario seleccionado
		  
		  @GetMapping("/user/{id}")
		  @PreAuthorize("hasAnyRole('ADMIN','USER')")
		 public Optional <DocumentRequest> foundDocumentationByUser (@PathVariable Long id) {
			 return docServ.findByUploadedBy(id);
		 }
		 
		 
		 // obtener las documentaciones
		 
		 @GetMapping("/")
		  @PreAuthorize("hasAnyRole('ADMIN','USER')")
		 public List <DocumentRequest> getDocumentsAll() {
			 return docServ.findAllDocuments();
		 }
		 
		 
		  
		//eliminar una documentacion
			 
		  @DeleteMapping("/{id}")
		  @PreAuthorize("hasAnyRole('ADMIN')")
		  public void deleteDocumentation(@PathVariable Long id) {
			  docServ.delete(id);
		  }
		 
	
	
}
