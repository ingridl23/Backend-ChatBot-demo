package com.chat.demo.service.rag;

import java.util.List;
import java.util.Optional;

import com.chat.demo.model.Area;

public interface AreaService {

    Area save(Area area);

    Area update(Long id, Area area);

    Optional<Area> findById(Long id);

    Optional<Area> findByName(String name);

    List<Area> findByOrganization(Long organizationId);

    void delete(Long id);
}
