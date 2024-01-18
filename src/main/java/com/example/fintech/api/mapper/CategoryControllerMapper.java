package com.example.fintech.api.mapper;

import com.example.fintech.api.request.CategoryRequest;
import com.example.fintech.api.response.CategoryResponse;
import com.example.fintech.model.category.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryControllerMapper {

	Category toCategory(CategoryRequest request);

	CategoryResponse toCategoryResponse(Category result);
}
