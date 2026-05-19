package com.chat.demo.service.rag.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.chat.demo.model.DocumentStatus;
import com.chat.demo.repository.DocumentStatusRepository;
import com.chat.demo.service.rag.DocumentStatusService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentStatusServiceImpl implements DocumentStatusService {

	private final DocumentStatusRepository documentRepository;
	@Override
	public DocumentStatus save(DocumentStatus status) {
		 return documentRepository.save(status);
	}

	@Override
	public Optional<DocumentStatus> findById(Long id) {
	    return documentRepository.findById(id);
	}

	@Override
	public Optional<DocumentStatus> findByName(String name) {
	   return documentRepository.findByName(name);
	}

	@Override
	public List<DocumentStatus> findAll() {
		return documentRepository.findAll();
	}

	@Override
	public void delete(Long id) {

		documentRepository.deleteById(id);
		
	}

}
