package com.chat.demo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.chat.demo.model.SenderType;



public interface SenderTypeRepository extends JpaRepository<SenderType, Long>{

	Optional<SenderType>findByName (String name);
}
