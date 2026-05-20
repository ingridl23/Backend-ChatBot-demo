package com.chat.demo.mapper;

import org.springframework.stereotype.Component;

import com.chat.demo.dto.FaqRequest;
import com.chat.demo.dto.FaqResponse;
import com.chat.demo.model.Faq;

@Component
public class FaqMapper {
	public Faq toEntity(FaqRequest dto) {

        return Faq.builder()
                .question(dto.getQuestion())
                .answer(dto.getAnswer())
                .build();
    }

    public FaqResponse toResponse(Faq faq) {

        FaqResponse dto = new FaqResponse();

        dto.setId(faq.getId());
        dto.setQuestion(faq.getQuestion());

        return dto;
    }
}
