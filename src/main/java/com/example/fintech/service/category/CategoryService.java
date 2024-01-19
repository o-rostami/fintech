package com.example.fintech.service.category;

import com.example.fintech.model.category.Category;
import com.example.fintech.service.category.request.CategoryInitModel;
import com.example.fintech.service.category.response.CategoryDetailResult;
import com.example.fintech.service.category.response.CategoryInitResult;

public interface CategoryService {

	CategoryInitResult create(CategoryInitModel model);

	CategoryDetailResult getCategoryDetail(Long categoryId);
	Category getCategory(Long categoryId);

}
