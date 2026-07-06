package com.chat.demo.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.chat.demo.model.DocumentStored;


public interface DocumentRepository extends JpaRepository<DocumentStored, Long>{

	    Optional<DocumentStored>findByTitle(String title);

	    List<DocumentStored> findByOrganizationId(Long organizationId);

	    List<DocumentStored> findByAreasId(Long areaId);

	    List<DocumentStored> findByUploadedById(Long userId);

	    // Documentos del área del caller o globales (sin área asignada) — mismo criterio que
	    // usa el retrieval del chat (ver DocumentChunkRepository.findByOrganizationAndAreaOrGlobal).
	    @Query("""
	        SELECT d FROM DocumentStored d
	        WHERE d.organization.id = :organizationId
	          AND ( d.areas IS EMPTY
	                OR EXISTS (SELECT a FROM d.areas a WHERE a.id = :areaId) )
	        """)
	    List<DocumentStored> findByOrganizationAndAreaOrGlobal(
	            @Param("organizationId") Long organizationId,
	            @Param("areaId") Long areaId);

}