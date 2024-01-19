package com.example.fintech.api.expense;

import com.example.fintech.api.expense.mapper.ExpenseControllerMapper;
import com.example.fintech.api.expense.request.ExpenseRequest;
import com.example.fintech.api.expense.response.ExpenseResponse;
import com.example.fintech.service.expense.ExpenseService;
import com.example.fintech.service.expense.response.ExpenseInitResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/expense")
@PreAuthorize("hasRole('USER')")
@Slf4j
public class ExpenseController {

	private final ExpenseService expenseService;

	private final ExpenseControllerMapper mapper;

	@GetMapping
	@PreAuthorize("hasAuthority('user:read')")
	public String get() {
		return "GET:: user controller";
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('user:create')")
	public ResponseEntity<ExpenseResponse> addExpense(@RequestBody ExpenseRequest request) {
		log.info("add expense with: {}", request);
		var result = expenseService.addExpense(mapper.toExpenseInitModel(request));
		log.info("expense created successfully with: {}", result);
		return ResponseEntity.ok(mapper.toExpenseResponse(result));
	}


	@PutMapping
	@PreAuthorize("hasAuthority('user:update')")
	public String put() {
		return "PUT:: user controller";
	}

	@DeleteMapping
	@PreAuthorize("hasAuthority('user:delete')")
	public String delete() {
		return "DELETE:: user controller";
	}
}
