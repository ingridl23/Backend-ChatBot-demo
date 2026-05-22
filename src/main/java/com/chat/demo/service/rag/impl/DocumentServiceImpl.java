package com.chat.demo.service.rag.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.chat.demo.dto.DocumentRequest;
import com.chat.demo.mapper.DocumentMapper;

import com.chat.demo.model.Area;

import com.chat.demo.model.Document;
import com.chat.demo.model.DocumentStatus;

import com.chat.demo.repository.AreaRepository;
import com.chat.demo.repository.DocumentRepository;
import com.chat.demo.repository.DocumentStatusRepository;
import com.chat.demo.service.rag.DocumentService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService{
	
	private final DocumentRepository documentRepository;
	private final DocumentStatusRepository docStatusRepo;
	private final AreaRepository areaRepo;
	private final DocumentMapper mapper;

	@Override
	public DocumentRequest save(DocumentRequest document) {
		Document entity = mapper.toEntity(document);
	    
		  entity.setCreatedAt(LocalDateTime.now());
	        entity.setUploadedAt(LocalDateTime.now());

	        Document saved =
	                documentRepository.save(entity);

	        return mapper.toResponse(saved);
	}

	@Override
	public DocumentRequest update(Long id, DocumentRequest document) {

	    Document existing = documentRepository.findById(id)
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

	    Document saved =
	            documentRepository.save(existing);

	    return mapper.toResponse(saved);
	}
	
	
	@Override
	public Optional<DocumentRequest> findById(Long id) {

		   return documentRepository.findById(id)
		            .map(mapper::toResponse);
	}

	@Override
	public Optional<DocumentRequest> findByTitle(String title) {
		  Optional<Document> doc =
		            documentRepository.findByTitle(title);

		    return doc.stream()
		            .findFirst()
		            .map(mapper::toResponse);
		
		
	}

	@Override
	public Optional<DocumentRequest> findByOrganization(Long organizationId) {
		 List<Document> documents =
		            documentRepository.findByOrganizationId(organizationId);

		    return documents.stream()
		            .findFirst()
		            .map(mapper::toResponse);
	}

	@Override
	public Optional<DocumentRequest> findByArea(Long areaId) {
		
		
	      List <Document> docs = documentRepository.findByAreaId(areaId);
	      
	      return docs.stream().findFirst().map(mapper::toResponse);
	}

	@Override
	public Optional<DocumentRequest> findByUploadedBy(Long userId) {
	
		 List<Document> docs = documentRepository.findByUploadedById(userId);
		 return docs.stream().findFirst().map(mapper::toResponse);
	}

	@Override
	public void delete(Long id) {
		documentRepository.deleteById(id);
		
	}

	@Override
	public List<DocumentRequest> findAllDocuments() {
		return 	documentRepository.findAll()
	            .stream()
	            .map(mapper::toResponse)
	            .toList();
	
	}

}
