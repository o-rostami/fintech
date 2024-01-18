package com.example.fintech.service.category.impl;

import com.example.fintech.model.category.Category;
import com.example.fintech.model.category.dao.CategoryRepository;
import com.example.fintech.service.category.CategoryService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	@Override
	public Category create(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public Category getCategory(Long categoryId) {
		return categoryRepository.getReferenceById(categoryId);
	}
}
