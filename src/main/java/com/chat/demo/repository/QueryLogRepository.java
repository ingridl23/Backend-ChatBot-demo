package com.chat.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.demo.model.QueryLog;

public interface QueryLogRepository extends JpaRepository<QueryLog, Long>{

	Optional<QueryLog>findByQueryLogUser(String queryLog);
}
