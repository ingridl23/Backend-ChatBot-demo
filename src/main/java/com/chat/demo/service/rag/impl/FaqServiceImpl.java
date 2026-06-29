package com.chat.demo.service.rag.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chat.demo.dto.FaqRequest;
import com.chat.demo.dto.FaqResponse;
import com.chat.demo.mapper.FaqMapper;

import com.chat.demo.model.Faq;

import com.chat.demo.repository.FaqRepository;
import com.chat.demo.service.rag.FaqService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService{
	
	 private final FaqRepository faqRepo;
	 private final FaqMapper mapper;

	 
	 
		@Override
		public FaqResponse save(FaqRequest faq) {
			Faq entity = mapper.toEntity(faq);
		    
			  entity.setCreatedAt(LocalDateTime.now());
		        entity.setUploadedAt(LocalDateTime.now());

		        Faq saved = faqRepo.save(entity);

		        return mapper.toResponse(saved);
		}

		@Override
		public FaqResponse update(Long id, FaqRequest faq) {
			   Faq existing = faqRepo.findById(id)
		                .orElseThrow(() -> new RuntimeException("FAQ not found"));

		        existing.setQuestion(faq.getQuestion());
		        existing.setAnswer(faq.getAnswer());
		        existing.setPriority(faq.getPriority());
		        existing.setIsActive(faq.getIsActive());
		    	existing.setUploadedAt(LocalDateTime.now());
		        Faq saved =
		                faqRepo.save(existing);

		        return mapper.toResponse(saved);
		}



	@Override
	public Optional<FaqResponse> findById(Long id) {
		
	    return faqRepo.findById(id).map(mapper::toResponse);
	}

	@Override
	public Optional<FaqResponse> findByQuestion(String question) {
		return faqRepo.findByQuestion(question).map(mapper::toResponse);
	}

	@Override
	public List <FaqResponse>findByOrganization(Long organizationId) {
		return faqRepo.findByOrganizationId(organizationId)
				  .stream()
		            .map(mapper::toResponse)
		            .toList();
	}

	@Override
	public List <FaqResponse> findByArea(Long areaId) {
		return faqRepo.findByAreaId(areaId) 
				.stream()
	            .map(mapper::toResponse)
	            .toList();
	}

	@Override
	public List<FaqResponse> findActive() {
		return faqRepo.findByIsActiveTrue()  
				.stream()
	            .map(mapper::toResponse)
	            .toList();
	}

	@Override
	public void delete(Long id) {
		faqRepo.deleteById(id);
		
	}



}
