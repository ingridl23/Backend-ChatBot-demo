package com.chat.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.chat.demo.model.Faq;

public interface FaqRepository extends JpaRepository<Faq, Long>{

	Optional<Faq>findByFaqQuestion(String question);
	Optional<Faq>findByFaqAnswer(String answer);

}
