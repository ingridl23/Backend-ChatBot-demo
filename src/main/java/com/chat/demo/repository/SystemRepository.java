package com.chat.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.demo.model.System;

public interface SystemRepository extends JpaRepository<System, Long>{

	Optional<System>findBySystemName (String name);
}

