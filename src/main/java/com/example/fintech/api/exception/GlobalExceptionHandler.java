package com.example.fintech.api.exception;

import com.example.fintech.api.CustomErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
		log.error("unexpected error ", ex);
		return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public final ResponseEntity<Object> handleGeneralException(EntityNotFoundException ex, WebRequest request) {
		log.error("entity not found exception occurred with request {} and response ", request, ex);
		return buildResponseEntity(new CustomErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), ex));
	}

	private ResponseEntity<Object> buildResponseEntity(CustomErrorResponse response) {
		return new ResponseEntity<>(response, response.getStatus());
	}

}
