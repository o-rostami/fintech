package com.example.fintech.service.expense.mapper;

import com.example.fintech.model.expense.Expense;
import com.example.fintech.service.expense.request.ExpenseInitModel;
import com.example.fintech.service.expense.response.ExpenseDetailResult;
import com.example.fintech.service.expense.response.ExpenseInitResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExpenseServiceMapper {


	@Mapping(target = "amount", source = "model.amount")
	@Mapping(target = "title", source = "model.title")
	@Mapping(target = "description", source = "model.description")
	@Mapping(target = "effectiveDate", source = "model.effectiveDate")
	Expense toExpense(ExpenseInitModel model);

	@Mapping(target = "id", source = "expense.id")
	@Mapping(target = "title", source = "expense.title")
	@Mapping(target = "description", source = "expense.description")
	@Mapping(target = "categoryId", source = "expense.category.id")
	@Mapping(target = "version", source = "expense.version")
	@Mapping(target = "effectiveDate", source = "expense.effectiveDate")
	ExpenseInitResult toExpenseInitResult(Expense expense);

	@Mapping(target = "id", source = "expense.id")
	@Mapping(target = "title", source = "expense.title")
	@Mapping(target = "description", source = "expense.description")
	@Mapping(target = "categoryId", source = "expense.category.id")
	@Mapping(target = "version", source = "expense.version")
	@Mapping(target = "effectiveDate", source = "expense.effectiveDate")
	ExpenseDetailResult toExpenseDetailResult(Expense expense);
}
