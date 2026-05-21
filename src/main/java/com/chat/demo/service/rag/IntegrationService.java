package com.chat.demo.service.rag;
import java.util.List;
import java.util.Optional;
import com.chat.demo.model.Integration;

public interface IntegrationService {

	Integration save(Integration integration);

    Integration update(Long id, Integration integration);

    Optional<Integration> findById(Long id);

    Optional<Integration> findByName(String name);

    List<Integration> findByOrganization(Long organizationId);

   // List<Integration> findActiveByOrganization(Long organizationId);

    void deactivate(Long id);

    void delete(Long id);
}
