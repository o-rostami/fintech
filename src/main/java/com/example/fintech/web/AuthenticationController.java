package com.example.fintech.web;

import com.example.fintech.service.AuthenticationService;
import com.example.fintech.web.dto.reqeust.AuthenticationRequest;
import com.example.fintech.web.dto.reqeust.RegisterRequest;
import com.example.fintech.web.dto.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

	private final AuthenticationService authenticationService;


	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {

		return ResponseEntity.ok(authenticationService.register(request));
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(authenticationService.authenticate(request));

	}
}
