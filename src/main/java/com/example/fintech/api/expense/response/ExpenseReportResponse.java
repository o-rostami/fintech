package com.example.fintech.api.expense.response;

import java.util.List;

import com.example.fintech.api.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseReportResponse extends BaseResponse {

	List<ExpenseReportDto> data;

	private long totalElements;

	private long totalPages;

}
