package com.chat.demo.service.rag;
import java.util.List;
import java.util.Optional;
import com.chat.demo.model.QueryLog;

public interface QueryLogService {

    QueryLog save(QueryLog queryLog);

    Optional<QueryLog> findById(Long id);

    List<QueryLog> findByUser(Long userId);

    List<QueryLog> findByOrganization(Long organizationId);

    List<QueryLog> findFailedQueries();

    void delete(Long id);

}
