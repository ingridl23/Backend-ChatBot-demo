package com.chat.demo.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.chat.demo.model.Integration;

public interface IntegrationRepository extends JpaRepository<Integration, Long>{

	Optional<Integration>findByName(String name);
	
	List<Integration> findByOrganizationId(Long organizationId);

    List<Integration> findByOrganizationIdAndIsActiveTrue(Long organizationId);

    List<Integration> findByTypeId(Long typeId);
}
