package com.chat.demo.service.rag;

import java.util.List;
import java.util.Optional;

import com.chat.demo.model.SystemLink;

public interface SystemService {
	
	SystemLink save(SystemLink systemLink);

    SystemLink update(Long id, SystemLink systemLink);

    Optional<SystemLink> findById(Long id);

    Optional<SystemLink> findByName(String name);

    List<SystemLink> findByOrganization(Long organizationId);

    List<SystemLink> findByArea(Long areaId);

    List<SystemLink> findActiveByOrganization(Long organizationId);

    void desactivate(Long id);

    void delete(Long id);

}
