package com.chat.demo.service.rag;

import java.util.List;
import java.util.Optional;

import com.chat.demo.model.IntegrationType;

public interface IntegrationTypeService {

	   IntegrationType save(IntegrationType type);

	    Optional<IntegrationType> findById(Long id);

	    Optional<IntegrationType> findByName(String name);

	    List<IntegrationType> findAll();

	    void delete(Long id);
}
