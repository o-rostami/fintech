package com.example.fintech.api.expense;

import com.example.fintech.api.expense.mapper.ExpenseControllerMapper;
import com.example.fintech.api.expense.request.ExpenseRequest;
import com.example.fintech.api.expense.response.ExpenseReportResponse;
import com.example.fintech.api.expense.response.ExpenseResponse;
import com.example.fintech.service.expense.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/expense")
@PreAuthorize("hasRole('USER')")
@Slf4j
public class ExpenseController {

	private final ExpenseService expenseService;

	private final ExpenseControllerMapper mapper;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('user:create')")
	public ResponseEntity<ExpenseResponse> addExpense(@RequestBody ExpenseRequest request) {
		log.info("add expense with: {}", request);
		var result = expenseService.addExpense(mapper.toExpenseInitModel(request));
		log.info("expense created successfully with: {}", result);
		return ResponseEntity.ok(mapper.toExpenseResponse(result));
	}

	@GetMapping(path = "/{expenseId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<ExpenseResponse> getExpense(@PathVariable Long expenseId) {
		log.info("got request with id: {}", expenseId); var result = expenseService.getExpenseDetail(expenseId);
		log.info("expense created successfully with: {}", result);
		return ResponseEntity.ok(mapper.toExpenseResponse(result));
	}

	@PostMapping(path = "/report", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
			MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<ExpenseReportResponse> getReport(@RequestParam(name = "pageNum", defaultValue = "0") final Integer pageNum, @RequestParam(name = "pageSize", defaultValue = "10") final Integer pageSize) {
		log.info("get report with page size : {} and page number {}", pageSize, pageNum);
		var report = expenseService.getReport(pageNum, pageSize);
		ExpenseReportResponse response =mapper.toExpenseReportResponse(report);
		log.info("get report generated successfully with: {}", response);
		return ResponseEntity.ok(response);
	}

}
