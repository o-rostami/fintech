package com.example.fintech.api.category;

import com.example.fintech.api.auth.dto.reqeust.RegisterRequest;
import com.example.fintech.api.auth.dto.response.AuthenticationResponse;
import com.example.fintech.api.category.request.CategoryRequest;
import com.example.fintech.api.category.response.CategoryResponse;
import com.example.fintech.model.category.dao.CategoryRepository;
import com.example.fintech.model.token.dao.TokenRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
class CategoryControllerTest {


	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	CategoryRepository categoryRepository;

	private String accessToken;

	@BeforeEach
	void login() {
		tokenRepository.deleteAll();
		categoryRepository.deleteAll();
		RegisterRequest request = RegisterRequest.builder().userName("test").password("password").build();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<RegisterRequest> entity = new HttpEntity<>(request, headers);
		ResponseEntity<AuthenticationResponse> response = restTemplate.exchange(
				"/auth/authenticate", HttpMethod.POST, entity, AuthenticationResponse.class);
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getAccessToken()).isNotNull();
		assertThat(response.getBody().getRefreshToken()).isNotNull();
		accessToken = response.getBody().getAccessToken();
	}

	@AfterEach
	void logOut() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		HttpEntity<RegisterRequest> entity = new HttpEntity<>(headers);
		ResponseEntity<AuthenticationResponse> response = restTemplate.exchange(
				"/auth/logout", HttpMethod.POST, entity, AuthenticationResponse.class);
		tokenRepository.deleteAll();
		categoryRepository.deleteAll();
	}


	@Test
	@DisplayName("create - create category with user who has admin role successfully")
	public void createCategory_0() {
		String title = "t1";
		String desc = "desc";
		CategoryRequest request = CategoryRequest.builder().title(title).description(desc).build();
		HttpHeaders headers = new HttpHeaders(); ;
		headers.setBearerAuth(accessToken);
		HttpEntity<CategoryRequest> entity = new HttpEntity<>(request, headers);
		ResponseEntity<CategoryResponse> response = restTemplate.exchange(
				"/admin/category", HttpMethod.POST, entity, CategoryResponse.class);
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getId()).isNotNull();
		assertThat(response.getBody().getTitle()).isEqualTo(title);
		assertThat(response.getBody().getDescription()).isEqualTo(desc);
	}

	@Test
	@DisplayName("create - create category without admin role so get forbidden error")
	public void createCategory_1() {
		String title = "t1";
		String desc = "desc";
		CategoryRequest request = CategoryRequest.builder().title(title).description(desc).build();
		HttpHeaders headers = new HttpHeaders(); ;
		HttpEntity<CategoryRequest> entity = new HttpEntity<>(request, headers);
		ResponseEntity<CategoryResponse> response = restTemplate.exchange(
				"/admin/category", HttpMethod.POST, entity, CategoryResponse.class);
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}

	@Test
	@DisplayName("create - get category with user who has admin role with id which doesn't exsit")
	public void getCategory_0() {
		HttpHeaders headers = new HttpHeaders(); ;
		headers.setBearerAuth(accessToken);
		HttpEntity<CategoryRequest> entity = new HttpEntity<>(headers);
		ResponseEntity<CategoryResponse> response = restTemplate.exchange(
				"/admin/category/1", HttpMethod.GET, entity, CategoryResponse.class);
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

}
