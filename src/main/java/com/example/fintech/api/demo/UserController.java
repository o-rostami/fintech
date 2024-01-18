package com.example.fintech.api.demo;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/demo/user")
@PreAuthorize("hasRole('USER')")
public class UserController {

	@GetMapping
	@PreAuthorize("hasAuthority('user:read')")
	public String get() {
		return "GET:: user controller";
	}

	@PostMapping
	@PreAuthorize("hasAuthority('user:create')")
	public String post() {
		return "POST:: user controller";
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
