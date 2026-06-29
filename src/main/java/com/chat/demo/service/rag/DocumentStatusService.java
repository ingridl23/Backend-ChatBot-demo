package com.chat.demo.service.rag;

import java.util.List;
import java.util.Optional;

import com.chat.demo.model.DocumentStatus;

public interface DocumentStatusService {

	 DocumentStatus save(DocumentStatus status);

	    Optional<DocumentStatus> findById(Long id);

	    Optional<DocumentStatus> findByName(String name);

	    List<DocumentStatus> findAll();

	    void delete(Long id);
}
