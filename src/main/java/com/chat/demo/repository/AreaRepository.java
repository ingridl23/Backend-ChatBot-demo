package com.chat.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chat.demo.model.Area;



public interface AreaRepository extends JpaRepository<Area, Long>{

	@Query("SELECT a FROM Area a LEFT JOIN FETCH a.organization WHERE a.name = :name")
	Optional<Area> findByName(@Param("name") String name);

	@Query("SELECT a FROM Area a LEFT JOIN FETCH a.organization WHERE a.organization.id = :organizationId")
	List<Area> findByOrganizationId(@Param("organizationId") Long organizationId);
}
