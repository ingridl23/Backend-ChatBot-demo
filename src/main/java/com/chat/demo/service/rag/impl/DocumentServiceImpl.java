package com.chat.demo.service.rag.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.chat.demo.dto.DocumentRequest;
import com.chat.demo.dto.DocumentResponse;
import com.chat.demo.mapper.DocumentMapper;

import com.chat.demo.model.Area;

import com.chat.demo.model.DocumentStored;
import com.chat.demo.model.Organization;
import com.chat.demo.model.User;
import com.chat.demo.model.DocumentStatus;

import com.chat.demo.repository.AreaRepository;
import com.chat.demo.repository.DocumentRepository;
import com.chat.demo.repository.DocumentStatusRepository;
import com.chat.demo.repository.OrganizationRepository;
import com.chat.demo.repository.UserRepository;
import com.chat.demo.service.rag.DocumentService;
import com.chat.demo.service.rag.RagService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService{
	
	private final DocumentRepository documentRepository;
	private final DocumentStatusRepository docStatusRepo;
	private final AreaRepository areaRepo;
	private final DocumentMapper mapper;
	private final OrganizationRepository organizationRepo;
    private final RagService ragService;
	private final UserRepository userRepo;
	@Override
	public DocumentResponse save(DocumentRequest document) {
		
		DocumentStored entity = mapper.toEntity(document);
	    
		DocumentStatus status = docStatusRepo.findById(document.getStatusId())
	            .orElseThrow(() -> new RuntimeException("Status not found"));

	    Area area = areaRepo.findById(document.getAreaId())
	            .orElseThrow(() -> new RuntimeException("Area not found"));

	    Organization organization = organizationRepo
	            .findById(document.getOrganizationId())
	            .orElseThrow(() -> new RuntimeException("Organization not found"));

	    User user = userRepo.findById(document.getUploadedById())
	            .orElseThrow(() -> new RuntimeException("User not found"));
	    
	    
	    entity.setStatus(status);
	    entity.setArea(area);
	    entity.setOrganization(organization);
	    entity.setUploadedBy(user);
		entity.setCreatedAt(LocalDateTime.now());
	    entity.setUploadedAt(LocalDateTime.now());

	        DocumentStored saved =
	                documentRepository.save(entity);

	        return mapper.toResponse(saved);
	}

	@Override
	public DocumentResponse update(Long id, DocumentRequest document) {

	    DocumentStored existing = documentRepository.findById(id)
	            .orElseThrow(() ->
	                    new RuntimeException("Document not found"));

	    existing.setTitle(document.getTitle());
	    existing.setFileSize(document.getFileSize());
	    existing.setFileName(document.getFileName());
	    existing.setMimeType(document.getMimeType());

	    // STATUS

	    if (document.getStatusId() != null) {

	        DocumentStatus docStatus =
	                docStatusRepo.findById(document.getStatusId())
	                .orElseThrow(() ->
	                        new RuntimeException("Document status not found"));

	        existing.setStatus(docStatus);
	    }

	    // AREA

	    if (document.getAreaId() != null) {

	        Area area =
	                areaRepo.findById(document.getAreaId())
	                .orElseThrow(() ->
	                        new RuntimeException("Area not found"));

	        existing.setArea(area);
	    }

	    DocumentStored saved =
	            documentRepository.save(existing);

	    return mapper.toResponse(saved);
	}
	
	
	@Override
	public List<DocumentResponse> findById(Long id) {

		   return documentRepository.findById(id).stream()
		            .map(mapper::toResponse).toList();
	}

	@Override
	public List<DocumentResponse> findByTitle(String title) {
		
		  return documentRepository.findByTitle(title)
				  .stream().map(mapper::toResponse).toList();
		
		
	}

	@Override
	public List<DocumentResponse> findByOrganization(Long organizationId) {
		  return documentRepository.findByOrganizationId(organizationId)
		            .stream()
		            .map(mapper::toResponse)
		            .toList();
	}

	@Override
	public List<DocumentResponse> findByArea(Long areaId) {
		
		
		 return  documentRepository.findByAreaId(areaId).stream().map(mapper::toResponse).toList();
	}

	@Override
	public List<DocumentResponse> findByUploadedBy(Long userId) {
	
		 return documentRepository.findByUploadedById(userId)
				 .stream().map(mapper::toResponse).toList();
	}

	@Override
	public void delete(Long id) {
		documentRepository.deleteById(id);
		
	}

	@Override
	public List<DocumentResponse> findAllDocuments() {
		return 	documentRepository.findAll()
	            .stream()
	            .map(mapper::toResponse)
	            .toList();
	
	}

	@Override
	public DocumentResponse upload(MultipartFile file, String title, Long organizationId, Long areaId, Long statusId,
			Long uploadedById) {
	
		 try {

		        // carpeta uploads /documents

		        String uploadDir = "uploads/";

		        Files.createDirectories(Paths.get(uploadDir));

		        // nombre archivo

		        String fileName =
		                System.currentTimeMillis()
		                + "_"
		                + file.getOriginalFilename();

		        // path final

		        Path filePath =
		                Paths.get(uploadDir, fileName);

		        // guardar físico

		        Files.copy(file.getInputStream(), filePath);

		        // buscar relaciones

		        DocumentStatus status =
		                docStatusRepo.findById(statusId)
		                .orElseThrow(() ->
		                        new RuntimeException("Status not found"));

		        Area area =
		                areaRepo.findById(areaId)
		                .orElseThrow(() ->
		                        new RuntimeException("Area not found"));

		        Organization organization =
		                organizationRepo.findById(organizationId)
		                .orElseThrow(() ->
		                        new RuntimeException("Organization not found"));

		        User user =
		                userRepo.findById(uploadedById)
		                .orElseThrow(() ->
		                        new RuntimeException("User not found"));

		        // crear entidad

		        DocumentStored document =
		                DocumentStored.builder()
		                .title(title)
		                .fileName(fileName)
		                .mimeType(file.getContentType())
		                .fileSize(file.getSize())
		                .filePath(filePath.toString())
		                .status(status)
		                .area(area)
		                .organization(organization)
		                .uploadedBy(user)
		                .createdAt(LocalDateTime.now())
		                .uploadedAt(LocalDateTime.now())
		                .build();

		        DocumentStored saved =
		                documentRepository.save(document);
		        System.out.println("DOCUMENTO GUARDADO ID = " + saved.getId());

		        System.out.println("ANTES DE RAG");

		        ragService.ingestDocument(saved.getId());

		        System.out.println("DESPUES DE RAG");
		        // RAG ingestion

		        ragService.ingestDocument(saved.getId());

		        return mapper.toResponse(saved);

		    } catch (Exception e) {

		        System.out.println("=================================");
		        System.out.println("ERROR REAL:");
		        System.out.println(e.getClass().getName());
		        System.out.println(e.getMessage());
		        e.printStackTrace();
		        System.out.println("=================================");

		        throw new RuntimeException("Upload failed", e);
		    }
		 
		 
		 /*
		 
		 catch (Exception e) {

		        throw new RuntimeException("Upload failed");
		    }
		
		*/
		
	}

}
