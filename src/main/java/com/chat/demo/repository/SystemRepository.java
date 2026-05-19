package com.chat.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.demo.model.SystemLink;

public interface SystemRepository extends JpaRepository<SystemLink, Long>{

	Optional<SystemLink>findByName (String name);
	
	 List<SystemLink> findByOrganizationId(Long organizationId);

	 List<SystemLink> findByAreaId(Long areaId);

	 List<SystemLink> findByOrganizationIdAndIsActiveTrue(Long organizationId);
}

