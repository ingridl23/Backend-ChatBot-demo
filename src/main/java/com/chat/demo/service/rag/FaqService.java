package com.chat.demo.service.rag;

import java.util.List;
import java.util.Optional;

import com.chat.demo.model.Faq;

public interface FaqService {

   	    Faq save(Faq faq);

	    Faq update(Long id, Faq faq);

	    Optional<Faq> findById(Long id);

	    Optional<Faq> findByQuestion(String question);

	    List<Faq> findByOrganization(Long organizationId);

	    List<Faq> findByArea(Long areaId);

	    List<Faq> findActive();

	    void delete(Long id);
}
