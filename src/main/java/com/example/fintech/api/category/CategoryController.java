package com.example.fintech.api.category;

import com.example.fintech.api.mapper.CategoryControllerMapper;
import com.example.fintech.api.request.CategoryRequest;
import com.example.fintech.api.response.CategoryResponse;
import com.example.fintech.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/category")
@PreAuthorize("hasRole('ADMIN')")
@Slf4j
public class CategoryController {

	private final CategoryService categoryService;

	private final CategoryControllerMapper mapper;

	@GetMapping(path = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('admin:read')")
		public ResponseEntity<CategoryResponse> getCategory(@PathVariable Long categoryId) {
		log.info("got request with id: {}", categoryId);
		var result = categoryService.getCategory(categoryId);
		log.info("category created successfully with: {}", result);
		return ResponseEntity.ok(mapper.toCategoryResponse(result));
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('admin:create')")
	public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest request) {
		log.info("got create request with: {}", request);
		var result = categoryService.create(mapper.toCategory(request));
		log.info("category created successfully with: {}", result);
		return ResponseEntity.ok(mapper.toCategoryResponse(result));
	}
}
