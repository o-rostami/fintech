package com.example.fintech.service.expense.response;

import lombok.Data;

@Data
public class ExpenseInitResult {

	private Long id;

	private String title;

	private String description;

	private Long version;
	private Long categoryId;

}
