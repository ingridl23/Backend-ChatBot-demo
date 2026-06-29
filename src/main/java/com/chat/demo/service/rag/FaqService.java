package com.chat.demo.service.rag;

import java.util.List;
import java.util.Optional;

import com.chat.demo.dto.FaqRequest;
import com.chat.demo.dto.FaqResponse;
import com.chat.demo.model.Faq;

public interface FaqService {

   	    FaqResponse save(FaqRequest faq);

   	 FaqResponse update(Long id, FaqRequest faq);

	    Optional<FaqResponse> findById(Long id);

	    Optional<FaqResponse> findByQuestion(String question);

	    List<FaqResponse> findByOrganization(Long organizationId);

	    List<FaqResponse> findByArea(Long areaId);

	    List<FaqResponse> findActive();

	    void delete(Long id);
}
