package com.chat.demo.mapper;

import org.springframework.stereotype.Component;

import com.chat.demo.dto.AreaResponse;
import com.chat.demo.model.Area;

@Component
public class AreaMapper {

    public AreaResponse toResponse(Area area) {
        AreaResponse dto = new AreaResponse();
        dto.setId(area.getId());
        dto.setName(area.getName());
        dto.setDescription(area.getDescription());
        if (area.getOrganization() != null) {
            dto.setOrganizationId(area.getOrganization().getId());
        }
        return dto;
    }
}
