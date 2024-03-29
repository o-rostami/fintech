package com.example.fintech.api.category.request;

import com.example.fintech.api.BaseRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class CategoryRequest extends BaseRequest {
	@NotBlank
	private String title;

	private String description;

}
