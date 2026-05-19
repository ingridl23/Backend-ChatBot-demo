package com.chat.demo.service.rag.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chat.demo.model.Category;
import com.chat.demo.repository.CategoryRepository;
import com.chat.demo.service.rag.CategoryService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

	  private final CategoryRepository categoryRepository;

	    @Override
	    public Category save(Category category) {
	        return categoryRepository.save(category);
	    }

	    @Override
	    public Category update(Long id, Category category) {

	        Category existing = categoryRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Category not found"));

	        existing.setName(category.getName());
	        existing.setParent(category.getParent());
	        existing.setOrderIndex(category.getOrderIndex());

	        return categoryRepository.save(existing);
	    }

	    @Override
	    public Optional<Category> findById(Long id) {
	        return categoryRepository.findById(id);
	    }

	    @Override
	    public Optional<Category> findByName(String name) {
	        return categoryRepository.findByName(name);
	    }

	    @Override
	    public List<Category> findByOrganization(Long organizationId) {
	        return categoryRepository.findByOrganizationId(organizationId);
	    }

	    @Override
	    public List<Category> findChildren(Long parentId) {
	        return categoryRepository.findByParentId(parentId);
	    }

	    @Override
	    public void delete(Long id) {
	        categoryRepository.deleteById(id);
	    }
}
