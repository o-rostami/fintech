package com.example.fintech.api;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import org.springframework.http.HttpStatus;


@Getter
public class CustomErrorResponse extends BaseResponse {

	private HttpStatus status;

	private String errorCode;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;

	private String message;

	private String debugMessage;

	private CustomErrorResponse() {
		timestamp = LocalDateTime.now();
	}

	public CustomErrorResponse(HttpStatus status) {
		this(); this.status = status;
	}

	public CustomErrorResponse(HttpStatus status, Throwable ex) {
		this(); this.status = status; this.message = "Unexpected error"; this.debugMessage = ex.getLocalizedMessage();
	}

	public CustomErrorResponse(HttpStatus status, String message, Throwable ex) {
		this(); this.status = status; this.message = message; this.debugMessage = ex.getLocalizedMessage();
	}
	public CustomErrorResponse(HttpStatus status, String message, Throwable ex, String errorCode) {
		this(); this.status = status; this.message = message; this.debugMessage = ex.getLocalizedMessage();
		this.errorCode = errorCode;
	}
}
