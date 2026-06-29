package com.chat.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.chat.demo.model.Faq;

public interface FaqRepository extends JpaRepository<Faq, Long>{

	 Optional<Faq>findByQuestion(String question);
	 
	 List<Faq> findByOrganizationId(Long organizationId);

	 List<Faq> findByAreaId(Long areaId);

	 List<Faq> findByIsActiveTrue();

	 List<Faq> findByOrganizationIdAndIsActiveTrue(Long organizationId);

}
