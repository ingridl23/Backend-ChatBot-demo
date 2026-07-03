package com.chat.demo.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.demo.model.AISettings;




public interface AISettingsRepository extends JpaRepository<AISettings, Long>{

	Optional<AISettings> findByModelName(String modelName);
	Optional<AISettings> findByOrganizationIdAndActiveTrue(Long organizationId);
	Optional<AISettings> findByOrganizationIdAndAreaIdAndActiveTrue(Long organizationId, Long areaId);
	Optional<AISettings> findByOrganizationIdAndAreaIsNullAndActiveTrue(Long organizationId);
	List<AISettings> findByOrganizationId(Long organizationId);
	List<AISettings> findAllByActiveTrue();
}
