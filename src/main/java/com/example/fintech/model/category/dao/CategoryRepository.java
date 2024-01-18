package com.example.fintech.model.category.dao;

import java.util.Optional;

import com.example.fintech.model.category.Category;
import com.example.fintech.model.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


}
