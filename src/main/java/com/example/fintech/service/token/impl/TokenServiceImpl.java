package com.example.fintech.service.token.impl;

import java.util.Optional;

import com.example.fintech.model.token.Token;
import com.example.fintech.model.token.TokenType;
import com.example.fintech.model.token.dao.TokenRepository;
import com.example.fintech.model.user.User;
import com.example.fintech.service.token.TokenService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

	private final TokenRepository tokenRepository;

	@Override
	public void saveUserToken(User user, String jwtToken) {
		var token = Token.builder().user(user).token(jwtToken).tokenType(TokenType.BEARER).revoked(false).expired(false).build();
		tokenRepository.save(token);
	}

	@Override
	public void revokeAllUserToken(User user) {
		var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
		if (validUserTokens.isEmpty()) {
			return;
		}
		validUserTokens.forEach(item -> {
			item.setExpired(true); item.setRevoked(true);
		});
		tokenRepository.saveAll(validUserTokens);
	}

	@Override
	public Optional<Token> findByToken(String jwtToken) {
		return tokenRepository.findByToken(jwtToken);
	}

	@Override
	public void save(Token storedToken) {
		tokenRepository.save(storedToken);
	}
}
