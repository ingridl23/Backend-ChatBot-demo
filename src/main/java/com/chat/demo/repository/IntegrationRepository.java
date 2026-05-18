package com.chat.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.demo.model.Integration;

public interface IntegrationRepository extends JpaRepository<Integration, Long>{

	Optional<Integration>findByIntegrationName(String name);
}
