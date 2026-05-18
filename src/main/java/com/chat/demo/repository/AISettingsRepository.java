package com.chat.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.demo.model.User;



public interface AISettingsRepository extends JpaRepository<User, Long>{

	Optional<User>findByModelname(String modelName);

}
