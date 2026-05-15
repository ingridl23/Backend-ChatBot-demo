package com.chat.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chat.demo.model.Role;

@Repository
public interface IroleRepository extends JpaRepository<Role, Long>{
	 Optional<Role> findByName(String name);
}
