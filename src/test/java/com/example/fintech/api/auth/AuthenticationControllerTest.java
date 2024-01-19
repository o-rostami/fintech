package com.example.fintech.api.auth;

import com.example.fintech.api.auth.dto.reqeust.RegisterRequest;
import com.example.fintech.api.auth.dto.response.AuthenticationResponse;
import com.example.fintech.model.user.Role;
import com.example.fintech.model.user.dao.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest {


	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	UserRepository userRepository;

	@Test
	@DisplayName("register - register user with user role and get access token with refresh token")
	public void register_0() {
		RegisterRequest request = RegisterRequest.builder().userName("t1").password("p").build();
		HttpHeaders headers = new HttpHeaders(); ;
		HttpEntity<RegisterRequest> entity = new HttpEntity<>(request, headers);
		ResponseEntity<AuthenticationResponse> response = restTemplate.exchange(
				"/auth/register", HttpMethod.POST, entity, AuthenticationResponse.class);
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getAccessToken()).isNotNull();
		assertThat(response.getBody().getRefreshToken()).isNotNull();

		var createdUser = userRepository.findByUserName("t1");
		createdUser.ifPresent(
				item -> {
					assertThat(item.getRole()).isNotNull();
					assertThat(item.getRole()).isEqualTo(Role.USER);
				}
		);
	}

	@Test
	@DisplayName("register - register user with already existed user name so that the error return")
	public void register_1() {
		RegisterRequest request = RegisterRequest.builder().userName("test").password("password").build();
		HttpHeaders headers = new HttpHeaders(); ;
		HttpEntity<RegisterRequest> entity = new HttpEntity<>(request, headers);
		ResponseEntity<AuthenticationResponse> response = restTemplate.exchange(
				"/auth/register", HttpMethod.POST, entity, AuthenticationResponse.class);
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
		assertThat(response.getBody()).isNull();
	}


	@Test
	@DisplayName("authenticate - authenticate already existed user and get access token and refresh token")
	public void authenticate_0() {
		RegisterRequest request = RegisterRequest.builder().userName("test").password("password").build();
		HttpHeaders headers = new HttpHeaders(); ;
		HttpEntity<RegisterRequest> entity = new HttpEntity<>(request, headers);
		ResponseEntity<AuthenticationResponse> response = restTemplate.exchange(
				"/auth/authenticate", HttpMethod.POST, entity, AuthenticationResponse.class);
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getAccessToken()).isNotNull();
		assertThat(response.getBody().getRefreshToken()).isNotNull();
	}

}
