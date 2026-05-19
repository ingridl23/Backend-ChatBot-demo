package com.chat.demo.service.rag;

import java.util.List;
import java.util.Optional;

import com.chat.demo.model.Category;

public interface CategoryService {
	
	   Category save(Category category);

	    Category update(Long id, Category category);

	    Optional<Category> findById(Long id);

	    Optional<Category> findByName(String name);

	    List<Category> findByOrganization(Long organizationId);

	    List<Category> findChildren(Long parentId);

	    void delete(Long id);

}
