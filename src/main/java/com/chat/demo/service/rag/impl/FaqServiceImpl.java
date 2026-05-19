package com.chat.demo.service.rag.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chat.demo.model.Faq;
import com.chat.demo.repository.DocumentStatusRepository;
import com.chat.demo.repository.FaqRepository;
import com.chat.demo.service.rag.FaqService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService{
	
	 private final FaqRepository faqRepo;

	@Override
	public Faq save(Faq faq) {
		return faqRepo.save(faq);
	}

	@Override
	public Faq update(Long id, Faq faq) {
		   Faq existing = faqRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("FAQ not found"));

	        existing.setQuestion(faq.getQuestion());
	        existing.setAnswer(faq.getAnswer());
	        existing.setPriority(faq.getPriority());
	        existing.setIsActive(faq.getIsActive());

	        return faqRepo.save(existing);
	}

	@Override
	public Optional<Faq> findById(Long id) {
	    return faqRepo.findById(id);
	}

	@Override
	public Optional<Faq> findByQuestion(String question) {
		return faqRepo.findByQuestion(question);
	}

	@Override
	public List<Faq> findByOrganization(Long organizationId) {
		return faqRepo.findByOrganizationId(organizationId);
	}

	@Override
	public List<Faq> findByArea(Long areaId) {
		return faqRepo.findByAreaId(areaId);
	}

	@Override
	public List<Faq> findActive() {
		return faqRepo.findByIsActiveTrue();
	}

	@Override
	public void delete(Long id) {
		faqRepo.deleteById(id);
		
	}

}
