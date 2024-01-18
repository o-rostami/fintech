package com.example.fintech.service.token;

import java.util.Optional;

import com.example.fintech.model.token.Token;
import com.example.fintech.model.user.User;


public interface TokenService {


	void saveUserToken(User user, String jwtToken);

	void revokeAllUserToken(User user);

	Optional<Token> findByToken(String jwtToken);

	void save(Token storedToken);
}
