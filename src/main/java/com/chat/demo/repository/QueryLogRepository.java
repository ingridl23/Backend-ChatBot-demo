package com.chat.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.demo.model.QueryLog;

public interface QueryLogRepository extends JpaRepository<QueryLog, Long>{
	
    List<QueryLog> findByUserId(Long userId);

    List<QueryLog> findByOrganizationId(Long organizationId);

    List<QueryLog> findBySuccess(Boolean success);
}
