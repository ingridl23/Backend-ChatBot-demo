package com.chat.demo.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.chat.demo.model.AISettings;




public interface AISettingsRepository extends JpaRepository<AISettings, Long>{

	Optional<AISettings>findByModelname(String modelName);
	Optional<AISettings> findByOrganizationIdAndActiveTrue(Long organizationId);
	List<AISettings> findByOrganizationId(Long organizationId);
}
