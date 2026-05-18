package com.chat.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.demo.model.Area;



public interface AreaRepository extends JpaRepository<Area, Long>{

	Optional<Area>findByAreaName(String name);
}
