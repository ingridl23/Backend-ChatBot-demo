package com.chat.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.chat.demo.model.DocumentChunk;

public interface DocumentChunkRepository extends JpaRepository<DocumentChunk, Long>{

    List<DocumentChunk> findByDocumentId(Long documentId);
    List<DocumentChunk> findByDocumentOrganizationId(Long organizationId);
    List<DocumentChunk> findByEmbeddingId(String embedding);
	List<DocumentChunk> findByChunkIndex(Integer chunkIndex);
	List<DocumentChunk> findTop5ByDocumentOrganizationId(Long organizationId);
}
