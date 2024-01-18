package com.example.fintech.api.category.mapper;

import com.example.fintech.api.category.request.CategoryRequest;
import com.example.fintech.api.category.response.CategoryResponse;
import com.example.fintech.model.category.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryControllerMapper {

	Category toCategory(CategoryRequest request);

	CategoryResponse toCategoryResponse(Category result);
}
