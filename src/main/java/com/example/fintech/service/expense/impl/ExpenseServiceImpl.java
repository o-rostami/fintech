package com.example.fintech.service.expense.impl;

import com.example.fintech.model.expense.dao.ExpenseRepository;
import com.example.fintech.service.category.CategoryService;
import com.example.fintech.service.expense.ExpenseService;
import com.example.fintech.service.expense.mapper.ExpenseServiceMapper;
import com.example.fintech.service.expense.request.ExpenseInitModel;
import com.example.fintech.service.expense.response.ExpenseDetailResult;
import com.example.fintech.service.expense.response.ExpenseInitResult;
import com.example.fintech.service.expense.response.MonthlyExpenseResult;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

	private final ExpenseRepository expenseRepository;

	private final CategoryService categoryService;

	private final ExpenseServiceMapper expenseServiceMapper;


	@Override
	public ExpenseInitResult addExpense(ExpenseInitModel model) {
		var category = categoryService.getCategory(model.getCategoryId());
		var expense = expenseServiceMapper.toExpense(model);
		expense.setCategory(category);
		return expenseServiceMapper.toExpenseInitResult(expenseRepository.save(expense));
	}

	@Override
	public ExpenseDetailResult getExpenseDetail(Long expenseId) {
		return expenseServiceMapper.toExpenseDetailResult(expenseRepository.getReferenceById(expenseId));
	}


	@Override
	public Page<MonthlyExpenseResult> getReport(Integer pageNum, Integer pageSize) {
		Pageable page = PageRequest.of(pageNum, pageSize);
		return expenseRepository.findMonthlyExpenseReports(page);
	}

}
