package com.example.fintech.api.category;

import com.example.fintech.api.CategoryController;
import com.example.fintech.service.category.CategoryService;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CategoryResourceIT {

	@Autowired
	CategoryController categoryController;

	@Autowired
	CategoryService categoryService;

}
