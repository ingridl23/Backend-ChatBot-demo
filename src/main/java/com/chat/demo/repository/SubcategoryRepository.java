package com.chat.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.demo.model.SubCategory;

public interface SubcategoryRepository extends JpaRepository<SubCategory, Long>{

	Optional<SubCategory>findBySubCategoryName (String name);
}
