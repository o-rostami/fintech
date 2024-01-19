package com.example.fintech.model.expense.dao;

import com.example.fintech.model.expense.Expense;
import com.example.fintech.service.expense.response.MonthlyExpenseResult;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {


	@Query(value = "select new com.example.fintech.service.expense.response.MonthlyExpenseResult(DATE_FORMAT(e" +
			".effectiveDate, '%Y-%m'), sum(e.amount),e.category.id) from Expense e "
			+ "group by DATE_FORMAT(e.effectiveDate, '%Y-%m'), e.category.id order by DATE_FORMAT(e.effectiveDate, "
			+ "'%Y-%m') asc ")
	Page<MonthlyExpenseResult> findMonthlyExpenseReports(Pageable pageable);

}
