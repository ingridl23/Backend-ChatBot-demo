package com.chat.demo.service.rag.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chat.demo.model.IntegrationType;
import com.chat.demo.service.rag.IntegrationTypeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IntegrationTypeServiceImpl  implements IntegrationTypeService {
	
	private final IntegrationTypeService integraTyRepo;
	
	@Override
	public IntegrationType save(IntegrationType type) {
		 return integraTyRepo.save(type);
	}

	@Override
	public Optional<IntegrationType> findById(Long id) {
		 return integraTyRepo.findById(id);
	}

	@Override
	public Optional<IntegrationType> findByName(String name) {
	     return integraTyRepo.findByName(name);
	}

	@Override
	public List<IntegrationType> findAll() {
		return integraTyRepo.findAll();
	}

	@Override
	public void delete(Long id) {
		integraTyRepo.delete(id);
		
	}

}
