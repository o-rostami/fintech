package com.example.fintech.service.auth;

import java.io.IOException;
import java.util.Objects;

import com.example.fintech.api.auth.dto.reqeust.AuthenticationRequest;
import com.example.fintech.api.auth.dto.reqeust.RegisterRequest;
import com.example.fintech.api.auth.dto.response.AuthenticationResponse;
import com.example.fintech.model.user.Role;
import com.example.fintech.model.user.User;
import com.example.fintech.model.user.dao.UserRepository;
import com.example.fintech.service.token.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
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

	private final TokenService tokenService;

	public AuthenticationResponse register(RegisterRequest request) {
		var user = User.builder().userName(request.getUserName()).password(passwordEncoder.encode(request.getPassword())).role(Role.USER).build();
		var savedUser = userRepository.save(user);
		var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		tokenService.saveUserToken(savedUser, jwtToken);
		return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				request.getUserName(),
				request.getPassword()
		));
		var user = userRepository.findByUserName(request.getUserName()).orElseThrow(() -> new UsernameNotFoundException(
				"not found"));
		var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		tokenService.revokeAllUserToken(user);
		tokenService.saveUserToken(user, jwtToken);
		return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
	}


	public void refreshToken(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String refreshToken;
		final String userName;
		if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
			return;
		}
		refreshToken = authHeader.substring(7);
		userName = jwtService.extractUserName(refreshToken);
		if (userName != null) {
			var user = this.userRepository.findByUserName(userName)
					.orElseThrow();
			if (jwtService.isTokenValid(refreshToken, user)) {
				var accessToken = jwtService.generateToken(user);
				tokenService.revokeAllUserToken(user);
				tokenService.saveUserToken(user, accessToken);
				var authResponse = AuthenticationResponse.builder()
						.accessToken(accessToken)
						.refreshToken(refreshToken)
						.build();
				new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
			}
		}
	}

}
