package com.example.fintech.model.expense.dao;

import com.example.fintech.model.category.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Category, Long> {


}
