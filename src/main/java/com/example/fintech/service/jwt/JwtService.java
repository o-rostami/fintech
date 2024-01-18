package com.example.fintech.service.jwt;

import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Claims;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

	String extractUserName(String token);

	<T> T extractClaim(String token, Function<Claims, T> claimsResolver);

	String generateRefreshToken(UserDetails userDetails);

	String generateToken(UserDetails userDetails);

	String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

	boolean isTokenValid(String token, UserDetails userDetails);


}
