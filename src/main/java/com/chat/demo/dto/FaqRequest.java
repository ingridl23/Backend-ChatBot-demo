package com.chat.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqRequest {

	private String question;

    private String answer;

    private Long organizationId;

    private Long areaId;

    private Integer priority;

    private Boolean isActive;
}
