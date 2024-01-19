package com.example.fintech.api.expense.request;

import java.util.Date;

import com.example.fintech.api.BaseRequest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class ExpenseRequest extends BaseRequest {
	private Long amount;

	private Long categoryId;

	private String title;

	private String description;

	private Date effectiveDate;
}
