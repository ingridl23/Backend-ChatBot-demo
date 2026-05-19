package com.chat.demo.service.rag.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.chat.demo.model.QueryLog;
import com.chat.demo.repository.QueryLogRepository;
import com.chat.demo.service.rag.QueryLogService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QueryLogServiceImpl implements QueryLogService{
	
	private final QueryLogRepository  queryRepo;
	
	@Override
	public QueryLog save(QueryLog queryLog) {
		 return queryRepo.save(queryLog);
	}

	@Override
	public Optional<QueryLog> findById(Long id) {
	    return queryRepo.findById(id);
	}

	@Override
	public List<QueryLog> findByUser(Long userId) {
		 return queryRepo.findByUserId(userId);
	}

	@Override
	public List<QueryLog> findByOrganization(Long organizationId) {
		return queryRepo.findByOrganizationId(organizationId);
	}

	@Override
	public List<QueryLog> findFailedQueries() {
		return queryRepo.findBySuccess(false);
	}

	@Override
	public void delete(Long id) {
		queryRepo.deleteById(id);
		
	}

}
