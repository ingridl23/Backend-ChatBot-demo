package com.chat.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chat.demo.model.Permission;


@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>{
	 Optional<Permission> findByName(String name);
}

