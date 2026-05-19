package com.chat.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.chat.demo.model.Category;

public interface CategoryRepository extends JpaRepository <Category, Long>{

	Optional<Category>findByName(String name);
	List<Category> findByOrganizationId(Long organizationId);
	List<Category> findByParentId(Long parentId);
}
