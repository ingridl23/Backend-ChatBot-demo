package com.chat.demo.controller.rag;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.chat.demo.dto.DocumentRequest;
import com.chat.demo.dto.DocumentResponse;

import com.chat.demo.service.rag.DocumentService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

	private static final Logger log = LoggerFactory.getLogger(DocumentController.class);
	private final DocumentService docServ;
	//crear o guardar una configuracion
		  @PostMapping
		  @PreAuthorize("hasAnyRole('ADMIN')")
		    public DocumentResponse documentSave(@RequestBody DocumentRequest request) {
		        return docServ.save(request);
		    }				  
		
		// modificar una documentacion cargada
		
		 @PutMapping("/{id}")
		  @PreAuthorize("hasAnyRole('ADMIN')")
		 public DocumentResponse documentUpdate (@PathVariable Long id,@RequestBody DocumentRequest request) {
			 return docServ.update(id,request);
		 }
		
		 // buscar una documentacion por id
		 
		 @GetMapping("/{id}")
		  @PreAuthorize("hasAnyRole('ADMIN','USER')")
		 public List<DocumentResponse> foundDocumentationById (@PathVariable Long id) {
			 return docServ.findById(id);
		 }
		 
		 
		// buscar una documentacion por su titulo
		 
		 @GetMapping("/title/{title}")
		  @PreAuthorize("hasAnyRole('ADMIN','USER')")
		 public List<DocumentResponse> foundDocumentationByTitle (@PathVariable String title) {
			 return docServ.findByTitle(title);
		 }
		 
		 
		 // obtener la documentacion por organizacion
		  @GetMapping("/organization/{organizationId}")
		  @PreAuthorize("hasAnyRole('ADMIN','USER')")
		 public List <DocumentResponse> foundDocumentationByOrganization (@PathVariable Long organizationId) {
			 return docServ.findByOrganization(organizationId);
		 }
		 
		 
		 // obtener documentacion por area (oficinas o especialidades)
		 
		  @GetMapping("/area/{areaId}")
		  @PreAuthorize("hasAnyRole('ADMIN','USER')")
		 public List<DocumentResponse> foundDocumentationByArea (@PathVariable Long areaId) {
			 return docServ.findByArea(areaId);
		 }
		 
		 
		 // obtener documentacion cargada por un usuario seleccionado
		  
		  @GetMapping("/user/{id}")
		  @PreAuthorize("hasAnyRole('ADMIN','USER')")
		 public List<DocumentResponse> foundDocumentationByUser (@PathVariable Long id) {
			 return docServ.findByUploadedBy(id);
		 }
		 
		 
		 // obtener las documentaciones
		 
		 @GetMapping
		  @PreAuthorize("hasAnyRole('ADMIN','USER')")
		 public List <DocumentResponse> getDocumentsAll() {
			 return docServ.findAllDocuments();
		 }
		   
		//eliminar una documentacion
			 
		  @DeleteMapping("/{id}")
		  @PreAuthorize("hasAnyRole('ADMIN')")
		  public void deleteDocumentation(@PathVariable Long id) {
			  docServ.delete(id);
		  }
		 
		  
		  @PostMapping("/upload")
		  @PreAuthorize("hasRole('ADMIN')")
		  public DocumentResponse uploadDocument(
		          @RequestParam("filePath") MultipartFile filePath,
		          @RequestParam("title") String title,
		          @RequestParam("organizationId") Long organizationId,
		          @RequestParam("areaId") Long areaId,
		          @RequestParam("statusId") Long statusId,
		          @RequestParam("uploadedById") Long uploadedById
		  ) {
			  log.info("Upload request: title={}, organizationId={}", title, organizationId);
			  if (!filePath.getContentType().equals("application/pdf")) {
				    throw new RuntimeException("Only PDF files allowed");
				}
			  
			  if (filePath.getSize() > 10_000_000) {
				    throw new RuntimeException("File too large");
				}

		      return docServ.upload(
		              filePath,
		              title,
		              organizationId,
		              areaId,
		              statusId,
		              uploadedById
		      );
		  }
	
	
}
