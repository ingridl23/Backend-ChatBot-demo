package com.chat.demo.service.rag.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.chat.demo.model.Document;
import com.chat.demo.repository.DocumentRepository;
import com.chat.demo.service.rag.DocumentService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService{
	
	private final DocumentRepository documentRepository;

	@Override
	public Document save(Document document) {
	      return documentRepository.save(document);
	}

	@Override
	public Document update(Long id, Document document) {
		  Document existing = documentRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Document not found"));

	        existing.setTitle(document.getTitle());
	        existing.setFilePath(document.getFilePath());
	        existing.setStatus(document.getStatus());
	        existing.setArea(document.getArea());

	        return documentRepository.save(existing);
	}

	@Override
	public Optional<Document> findById(Long id) {
		return documentRepository.findById(id);
	}

	@Override
	public Optional<Document> findByTitle(String title) {
		return documentRepository.findByTitle(title);
	}

	@Override
	public List<Document> findByOrganization(Long organizationId) {
		return documentRepository.findByOrganizationId(organizationId);
	}

	@Override
	public List<Document> findByArea(Long areaId) {
	      return documentRepository.findByAreaId(areaId);
	}

	@Override
	public List<Document> findByUploadedBy(Long userId) {
	
		   return documentRepository.findByUploadedById(userId);
	}

	@Override
	public void delete(Long id) {
		documentRepository.deleteById(id);
		
	}

}
