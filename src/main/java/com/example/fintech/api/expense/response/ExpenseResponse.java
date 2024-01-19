package com.example.fintech.api.expense.response;

import com.example.fintech.api.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExpenseResponse extends BaseResponse {


	private Long id;

	private String title;

	private String description;

	private Long version;

}
