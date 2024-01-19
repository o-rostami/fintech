package com.example.fintech.api.expense;

import java.util.Date;

import com.example.fintech.api.auth.dto.reqeust.RegisterRequest;
import com.example.fintech.api.auth.dto.response.AuthenticationResponse;
import com.example.fintech.api.expense.request.ExpenseRequest;
import com.example.fintech.api.expense.response.ExpenseResponse;
import com.example.fintech.model.category.Category;
import com.example.fintech.model.category.dao.CategoryRepository;
import com.example.fintech.model.expense.Expense;
import com.example.fintech.model.expense.dao.ExpenseRepository;
import com.example.fintech.model.token.dao.TokenRepository;
import com.example.fintech.model.user.dao.UserRepository;
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
class ExpenseControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	ExpenseRepository expenseRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	UserRepository userRepository;

	private String accessToken;

	private Long userId;

	@BeforeEach
	void login() {
		tokenRepository.deleteAll();
		expenseRepository.deleteAll();
		String userName = "user1";
		String password = "password1";
		RegisterRequest request = RegisterRequest.builder().userName(userName).password(password).build();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<RegisterRequest> entity = new HttpEntity<>(request, headers);
		ResponseEntity<AuthenticationResponse> response = restTemplate.exchange(
				"/auth/register", HttpMethod.POST, entity, AuthenticationResponse.class);
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getAccessToken()).isNotNull();
		assertThat(response.getBody().getRefreshToken()).isNotNull();
		accessToken = response.getBody().getAccessToken();
		userId = userRepository.findByUserName(userName).get().getId();
	}

	@AfterEach
	void logOut() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		HttpEntity<RegisterRequest> entity = new HttpEntity<>(headers);
		ResponseEntity<AuthenticationResponse> response = restTemplate.exchange(
				"/auth/logout", HttpMethod.POST, entity, AuthenticationResponse.class);
		tokenRepository.deleteAll();
		userRepository.deleteById(userId);
		expenseRepository.deleteAll();
	}


	@Test
	@DisplayName("addExpense - create expense with category which is no present ")
	public void addExpense_0() {
		String title = "t1";
		String desc = "desc";
		ExpenseRequest request =
				ExpenseRequest.builder().title(title).description(desc).amount(1000L).categoryId(1L).build();
		HttpHeaders headers = new HttpHeaders(); ;
		headers.setBearerAuth(accessToken);
		HttpEntity<ExpenseRequest> entity = new HttpEntity<>(request, headers);
		ResponseEntity<ExpenseResponse> response = restTemplate.exchange(
				"/user/expense", HttpMethod.POST, entity, ExpenseResponse.class);
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@Test
	@DisplayName("addExpense - create expense with valid category id ")
	public void addExpense_1() {
		String title = "t1";
		String desc = "desc";
		Category category = Category.builder().title(title).description(desc).build();
		categoryRepository.save(category);
		assertThat(categoryRepository.findAll()).isNotNull();
		assertThat(categoryRepository.findAll().size()).isOne();
		ExpenseRequest request =
				ExpenseRequest.builder().title(title).description(desc).amount(1000L).effectiveDate(new Date().getTime()).categoryId(category.getId()).build();
		HttpHeaders headers = new HttpHeaders(); ;
		headers.setBearerAuth(accessToken);
		HttpEntity<ExpenseRequest> entity = new HttpEntity<>(request, headers);
		ResponseEntity<ExpenseResponse> response = restTemplate.exchange(
				"/user/expense", HttpMethod.POST, entity, ExpenseResponse.class);
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getId()).isNotNull();
		assertThat(response.getBody().getTitle()).isNotNull();
		assertThat(response.getBody().getTitle()).isEqualTo(title);
		assertThat(response.getBody().getDescription()).isNotNull();
		assertThat(response.getBody().getDescription()).isEqualTo(desc);
		assertThat(response.getBody().getEffectiveDate()).isNotNull();
	}

	@Test
	@DisplayName("getExpense - get expense with wrong id")
	public void getExpense_0() {
		HttpHeaders headers = new HttpHeaders(); ;
		headers.setBearerAuth(accessToken);
		HttpEntity<ExpenseRequest> entity = new HttpEntity<>(headers);
		ResponseEntity<ExpenseResponse> response = restTemplate.exchange(
				"/user/expense/1", HttpMethod.GET, entity, ExpenseResponse.class);
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	@DisplayName("getExpense - get expense with wrong id")
	public void getExpense_1() {
		String title = "t1";
		String desc = "desc";
		Category category = Category.builder().title(title).description(desc).build();
		categoryRepository.save(category);
		assertThat(categoryRepository.findAll()).isNotNull();
		assertThat(categoryRepository.findAll().size()).isOne();

		Expense expense =
				Expense.builder().amount(1000L).title(title).description(desc).amount(1000L).category(category).effectiveDate(new Date().getTime()).build();
		expenseRepository.save(expense);
		assertThat(expenseRepository.findAll()).isNotNull();
		assertThat(expenseRepository.findAll().size()).isOne();

		HttpHeaders headers = new HttpHeaders(); ;
		headers.setBearerAuth(accessToken);
		HttpEntity<ExpenseRequest> entity = new HttpEntity<>(headers);
		ResponseEntity<ExpenseResponse> response = restTemplate.exchange(
				"/user/expense/1", HttpMethod.GET, entity, ExpenseResponse.class);
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getId()).isNotNull();
		assertThat(response.getBody().getTitle()).isNotNull();
		assertThat(response.getBody().getTitle()).isEqualTo(title);
		assertThat(response.getBody().getDescription()).isNotNull();
		assertThat(response.getBody().getDescription()).isEqualTo(desc);
		assertThat(response.getBody().getEffectiveDate()).isNotNull();
	}

}
