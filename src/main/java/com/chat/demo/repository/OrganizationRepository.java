package com.chat.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.demo.model.Organization;


public interface OrganizationRepository extends JpaRepository<Organization, Long>{

		Optional<Organization>findByName(String name);
		Optional<Organization> findByDomain(String domain);
}
