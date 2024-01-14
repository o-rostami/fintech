package com.example.fintech.service;

import com.example.fintech.config.JwtService;
import com.example.fintech.model.user.Role;
import com.example.fintech.model.user.User;
import com.example.fintech.model.user.dao.UserRepository;
import com.example.fintech.web.dto.reqeust.AuthenticationRequest;
import com.example.fintech.web.dto.reqeust.RegisterRequest;
import com.example.fintech.web.dto.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthenticationResponse register(RegisterRequest request) {
		var user = User.builder().userName(request.getUserName()).password(passwordEncoder.encode(request.getPassword())).role(Role.USER).build();
		userRepository.save(user);
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				request.getUserName(),
				request.getPassword()
		));
		var user=userRepository.findByUserName(request.getUserName()).orElseThrow(()-> new UsernameNotFoundException(
				"not found"));
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}
}
