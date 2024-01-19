package com.example.fintech.service.expense.request;

import java.util.Date;

import lombok.Data;

@Data
public class ExpenseInitModel {

	private Long amount;

	private Long categoryId;

	private String title;

	private String description;

	private Date effectiveDate;
}
