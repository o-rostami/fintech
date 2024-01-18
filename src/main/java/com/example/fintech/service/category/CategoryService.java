package com.example.fintech.service.category;

import com.example.fintech.model.category.Category;

public interface CategoryService {

	Category create(Category category);

	Category getCategory(Long categoryId);

}
