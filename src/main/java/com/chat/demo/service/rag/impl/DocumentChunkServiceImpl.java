package com.chat.demo.service.rag.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.chat.demo.model.DocumentChunk;
import com.chat.demo.repository.DocumentChunkRepository;
import com.chat.demo.service.rag.DocumentChunkService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentChunkServiceImpl implements DocumentChunkService {

	private final DocumentChunkRepository  documentRepository; 
	@Override
	public DocumentChunk save(DocumentChunk chunk) {
		return documentRepository.save(chunk);
	}

	@Override
	public Optional<DocumentChunk> findById(Long id) {
		return documentRepository.findById(id);
	}

	@Override
	public List<DocumentChunk> findByChunkIndex(Integer chunkIndex) {
		return documentRepository.findByChunkIndex(chunkIndex);
	}

	@Override
	public List<DocumentChunk> findByDocument(Long documentId) {
		 return documentRepository.findByDocumentId(documentId);
	}

	@Override
	public List<DocumentChunk> findByDocumentOrganization(Long organizationId) {
	      
		return documentRepository.findByDocumentOrganizationId(organizationId);
	}

	@Override
	public List<DocumentChunk> findByEmbeddingId(String embeddingId) {
		 return documentRepository.findByEmbeddingId(embeddingId);
	}

	@Override
	public void delete(Long id) {
		documentRepository.deleteById(id);
	}

}
