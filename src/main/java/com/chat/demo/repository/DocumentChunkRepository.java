package com.chat.demo.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.chat.demo.model.DocumentChunk;

public interface DocumentChunkRepository extends JpaRepository<DocumentChunk, Long>{

    Optional<DocumentChunk>findByChuckIndex(Integer index);
    List<DocumentChunk> findByDocumentId(Long documentId);
    List<DocumentChunk> findByDocumentOrganizationId(Long organizationId);
    List<DocumentChunk> findByEmbeddingId(String embedding);
    
}
