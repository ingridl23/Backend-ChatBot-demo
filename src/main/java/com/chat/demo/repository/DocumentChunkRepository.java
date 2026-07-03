package com.chat.demo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.chat.demo.model.DocumentChunk;

public interface DocumentChunkRepository extends JpaRepository<DocumentChunk, Long>{

    List<DocumentChunk> findByDocumentId(Long documentId);
    void deleteByDocumentId(Long documentId);
    List<DocumentChunk> findByDocumentOrganizationId(Long organizationId);
    List<DocumentChunk> findByEmbeddingId(String embedding);
	List<DocumentChunk> findByChunkIndex(Integer chunkIndex);

	// Chunks de documentos del área del usuario o globales (documento sin áreas asignadas)
	@Query("""
	    SELECT c FROM DocumentChunk c
	    WHERE c.document.organization.id = :organizationId
	      AND ( c.document.areas IS EMPTY
	            OR EXISTS (SELECT a FROM c.document.areas a WHERE a.id = :areaId) )
	    """)
	List<DocumentChunk> findByOrganizationAndAreaOrGlobal(
	        @Param("organizationId") Long organizationId,
	        @Param("areaId") Long areaId,
	        Pageable pageable);

	// Solo documentos globales (para usuarios sin área asignada)
	@Query("""
	    SELECT c FROM DocumentChunk c
	    WHERE c.document.organization.id = :organizationId
	      AND c.document.areas IS EMPTY
	    """)
	List<DocumentChunk> findByOrganizationGlobalOnly(
	        @Param("organizationId") Long organizationId,
	        Pageable pageable);
}
