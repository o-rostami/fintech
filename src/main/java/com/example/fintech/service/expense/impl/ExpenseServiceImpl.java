package com.example.fintech.service.expense.impl;

import com.example.fintech.model.expense.dao.ExpenseRepository;
import com.example.fintech.service.expense.ExpenseService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

	private final ExpenseRepository expenseRepository;

}
