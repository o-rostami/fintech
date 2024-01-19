package com.example.fintech.api.expense.mapper;

import com.example.fintech.api.expense.request.ExpenseRequest;
import com.example.fintech.api.expense.response.ExpenseResponse;
import com.example.fintech.model.expense.Expense;
import com.example.fintech.service.expense.request.ExpenseInitModel;
import com.example.fintech.service.expense.response.ExpenseInitResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExpenseControllerMapper {


	ExpenseInitModel toExpenseInitModel(ExpenseRequest request);


	ExpenseResponse toExpenseResponse(ExpenseInitResult result);
}
