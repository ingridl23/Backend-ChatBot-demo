package com.chat.demo.service.rag.impl;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.chat.demo.model.SenderType;
import com.chat.demo.repository.SenderTypeRepository;
import com.chat.demo.service.rag.SenderTypeService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SenderTypeServiceImpl implements SenderTypeService{
	
	private final SenderTypeRepository senderRepo;
	
	@Override
	public SenderType save(SenderType senderType) {
		return senderRepo.save(senderType);
	}

	@Override
	public Optional<SenderType> findById(Long id) {
		  return senderRepo.findById(id);
	}

	@Override
	public Optional<SenderType> findByName(String name) {
		  return senderRepo.findByName(name);
	}

	@Override
	public List<SenderType> findAll() {
	     return senderRepo.findAll();
	}

	@Override
	public void delete(Long id) {
		  senderRepo.deleteById(id);
		
	}

}
