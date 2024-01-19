package com.example.fintech.api.expense.response;

import com.example.fintech.api.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.data.domain.Page;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExpenseReportDto extends BaseResponse {

	private Object yearMonth;

	private Long totalAmount;

	private Long categoryId;

}
