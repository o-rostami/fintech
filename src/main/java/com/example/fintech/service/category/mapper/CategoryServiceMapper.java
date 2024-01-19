package com.example.fintech.service.category.mapper;

import com.example.fintech.model.category.Category;
import com.example.fintech.service.category.request.CategoryInitModel;
import com.example.fintech.service.category.response.CategoryDetailResult;
import com.example.fintech.service.category.response.CategoryInitResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryServiceMapper {


	@Mapping(target = "title", source = "model.title")
	@Mapping(target = "description", source = "model.description")
	Category toCategory(CategoryInitModel model);

	CategoryInitResult toCategoryInitResult(Category category);

	CategoryDetailResult toCategoryDetailResult(Category category);
}
