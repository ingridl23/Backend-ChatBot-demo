package com.chat.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.demo.model.Area;



public interface AreaRepository extends JpaRepository<Area, Long>{

	Optional<Area>findByName(String name);

    List<Area> findByOrganizationId(Long organizationId);
}
