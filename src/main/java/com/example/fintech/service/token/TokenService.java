package com.example.fintech.service.token;

import java.util.Optional;

import com.example.fintech.model.token.Token;
import com.example.fintech.model.token.TokenType;
import com.example.fintech.model.token.dao.TokenRepository;
import com.example.fintech.model.user.User;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

	private final TokenRepository tokenRepository;

	public void saveUserToken(User user, String jwtToken) {
		var token = Token.builder().user(user).token(jwtToken).tokenType(TokenType.BEARER).revoked(false).expired(false).build();
		tokenRepository.save(token);
	}

	public void revokeAllUserToken(User user) {
		var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId()); if (validUserTokens.isEmpty()) {
			return;
		} validUserTokens.forEach(item -> {
			item.setExpired(true); item.setRevoked(true);
		}); tokenRepository.saveAll(validUserTokens);
	}

	public Optional<Token> findByToken(String jwtToken) {
		return tokenRepository.findByToken(jwtToken);
	}


	public void save(Token storedToken) {
		tokenRepository.save(storedToken);
	}
}
