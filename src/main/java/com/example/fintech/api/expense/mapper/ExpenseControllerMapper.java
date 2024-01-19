package com.example.fintech.api.expense.mapper;

import java.util.List;

import com.example.fintech.api.expense.request.ExpenseRequest;
import com.example.fintech.api.expense.response.ExpenseReportDto;
import com.example.fintech.api.expense.response.ExpenseReportResponse;
import com.example.fintech.api.expense.response.ExpenseResponse;
import com.example.fintech.service.expense.request.ExpenseInitModel;
import com.example.fintech.service.expense.response.ExpenseDetailResult;
import com.example.fintech.service.expense.response.ExpenseInitResult;
import com.example.fintech.service.expense.response.MonthlyExpenseResult;
import org.mapstruct.Mapper;

import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ExpenseControllerMapper {

	ExpenseInitModel toExpenseInitModel(ExpenseRequest request);

	ExpenseResponse toExpenseResponse(ExpenseInitResult result);

	ExpenseResponse toExpenseResponse(ExpenseDetailResult result);

	ExpenseReportDto toExpenseReportDto(MonthlyExpenseResult monthlyExpenseResult);

	default ExpenseReportResponse toExpenseReportResponse(Page<MonthlyExpenseResult> report){
		ExpenseReportResponse response =new ExpenseReportResponse();
		response.setData(report.map(this::toExpenseReportDto).toList());
		response.setTotalElements(report.getTotalElements());
		response.setTotalPages(report.getTotalPages());
		return response;
	}
}
