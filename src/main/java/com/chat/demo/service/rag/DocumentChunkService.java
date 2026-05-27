package com.chat.demo.service.rag;

import java.util.List;
import java.util.Optional;

import com.chat.demo.model.DocumentChunk;

public interface DocumentChunkService  {

	 DocumentChunk save(DocumentChunk chunk);

	    Optional<DocumentChunk> findById(Long id);

	    List<DocumentChunk> findByChunkIndex(Integer chunkIndex);

	    List<DocumentChunk> findByDocument(Long documentId);

	    List<DocumentChunk> findByDocumentOrganization(Long organizationId);

	    List<DocumentChunk> findByEmbeddingId(String embeddingId);

	    void delete(Long id);
}
