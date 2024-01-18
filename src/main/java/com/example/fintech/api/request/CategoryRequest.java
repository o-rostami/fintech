package com.example.fintech.api.request;

import com.example.fintech.api.BaseRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryRequest extends BaseRequest {
	@NotBlank
	private String title;

	private String description;

}
