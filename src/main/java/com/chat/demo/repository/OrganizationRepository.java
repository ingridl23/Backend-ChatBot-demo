package com.chat.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.demo.model.Organization;


public interface OrganizationRepository extends JpaRepository<Organization, Long>{

		Optional<Organization>findByOrganizationName(String name);
}
