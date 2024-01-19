package com.example.fintech.service.category.impl;

import com.example.fintech.model.category.Category;
import com.example.fintech.model.category.dao.CategoryRepository;
import com.example.fintech.service.category.CategoryService;
import com.example.fintech.service.category.mapper.CategoryServiceMapper;
import com.example.fintech.service.category.request.CategoryInitModel;
import com.example.fintech.service.category.response.CategoryDetailResult;
import com.example.fintech.service.category.response.CategoryInitResult;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	private final CategoryServiceMapper categoryServiceMapper;

	@Override
	public CategoryInitResult create(CategoryInitModel model) {
		var category = categoryServiceMapper.toCategory(model);
		return categoryServiceMapper.toCategoryInitResult(categoryRepository.save(category));
	}

	@Override
	public Category getCategory(Long categoryId) {
		return categoryRepository.getReferenceById(categoryId);
	}

	@Override
	public CategoryDetailResult getCategoryDetail(Long categoryId) {
		return categoryServiceMapper.toCategoryDetailResult(categoryRepository.getReferenceById(categoryId));
	}
}
