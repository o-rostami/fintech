package com.example.fintech.service.expense;

import com.example.fintech.service.expense.request.ExpenseInitModel;
import com.example.fintech.service.expense.response.ExpenseDetailResult;
import com.example.fintech.service.expense.response.ExpenseInitResult;

public interface ExpenseService {


	ExpenseInitResult addExpense(ExpenseInitModel model);

	ExpenseDetailResult getExpenseDetail(Long expenseId);
}
