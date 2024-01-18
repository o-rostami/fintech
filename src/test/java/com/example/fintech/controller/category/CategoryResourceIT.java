package com.example.fintech.controller.category;

import com.example.fintech.api.category.CategoryController;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CategoryResourceIT {

	@Autowired
	CategoryController categoryController;

}
