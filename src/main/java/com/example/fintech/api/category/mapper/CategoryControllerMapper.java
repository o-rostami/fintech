package com.example.fintech.api.category.mapper;

import com.example.fintech.api.category.request.CategoryRequest;
import com.example.fintech.api.category.response.CategoryResponse;
import com.example.fintech.service.category.request.CategoryInitModel;
import com.example.fintech.service.category.response.CategoryDetailResult;
import com.example.fintech.service.category.response.CategoryInitResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryControllerMapper {

	CategoryInitModel toCategoryInitModel(CategoryRequest request);

	CategoryResponse toCategoryResponse(CategoryDetailResult result);
	CategoryResponse toCategoryResponse(CategoryInitResult result);
}
