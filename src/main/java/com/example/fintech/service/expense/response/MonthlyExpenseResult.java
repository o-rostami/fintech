package com.example.fintech.service.expense.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyExpenseResult {

	private Object yearMonth;

	private Long totalAmount;

	private Long categoryId;

}
