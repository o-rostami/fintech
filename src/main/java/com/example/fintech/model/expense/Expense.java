package com.example.fintech.model.expense;


import com.example.fintech.model.Auditable;
import com.example.fintech.model.category.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_expense")
public class Expense extends Auditable {

	private Long amount;

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	private String title;

	private String description;

	private Long effectiveDate;

}
