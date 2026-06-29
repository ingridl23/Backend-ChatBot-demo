package com.chat.demo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.chat.demo.model.IntegrationType;

public interface IntegrationTypeRepository extends JpaRepository<IntegrationType, Long>{

	Optional<IntegrationType>findByName(String name);
}
