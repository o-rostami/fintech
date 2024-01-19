package com.example.fintech.api.expense;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.example.fintech.api.auth.dto.reqeust.RegisterRequest;
import com.example.fintech.api.auth.dto.response.AuthenticationResponse;
import com.example.fintech.api.expense.request.ExpenseRequest;
import com.example.fintech.api.expense.response.ExpenseReportResponse;
import com.example.fintech.api.expense.response.ExpenseResponse;
import com.example.fintech.model.category.Category;
import com.example.fintech.model.category.dao.CategoryRepository;
import com.example.fintech.model.expense.Expense;
import com.example.fintech.model.expense.dao.ExpenseRepository;
import com.example.fintech.model.token.dao.TokenRepository;
import com.example.fintech.model.user.dao.UserRepository;
import com.example.fintech.service.expense.ExpenseService;
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
import org.springframework.http.MediaType;
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

	@Autowired
	ExpenseService expenseService;

	private String accessToken;

	private Long userId;

	@BeforeEach
	void login() {
		tokenRepository.deleteAll(); expenseRepository.deleteAll(); String userName = "user1";
		String password = "password1";
		RegisterRequest request = RegisterRequest.builder().userName(userName).password(password).build();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<RegisterRequest> entity = new HttpEntity<>(request, headers);
		ResponseEntity<AuthenticationResponse> response = restTemplate.exchange("/auth/register", HttpMethod.POST, entity, AuthenticationResponse.class);
		assertThat(response).isNotNull(); assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull(); assertThat(response.getBody().getAccessToken()).isNotNull();
		assertThat(response.getBody().getRefreshToken()).isNotNull(); accessToken = response.getBody().getAccessToken();
		userId = userRepository.findByUserName(userName).get().getId();
	}

	@AfterEach
	void logOut() {
		HttpHeaders headers = new HttpHeaders(); headers.setBearerAuth(accessToken);
		HttpEntity<RegisterRequest> entity = new HttpEntity<>(headers);
		ResponseEntity<AuthenticationResponse> response = restTemplate.exchange("/auth/logout", HttpMethod.POST, entity, AuthenticationResponse.class);
		tokenRepository.deleteAll(); userRepository.deleteById(userId); expenseRepository.deleteAll();
		categoryRepository.deleteAll();
	}


	@Test
	@DisplayName("addExpense - create expense with category which is no present ")
	public void addExpense_0() {
		String title = "t1"; String desc = "desc";
		ExpenseRequest request = ExpenseRequest.builder().title(title).description(desc).amount(1000L).categoryId(1L).build();
		HttpHeaders headers = new HttpHeaders(); ; headers.setBearerAuth(accessToken);
		HttpEntity<ExpenseRequest> entity = new HttpEntity<>(request, headers);
		ResponseEntity<ExpenseResponse> response = restTemplate.exchange("/user/expense", HttpMethod.POST, entity, ExpenseResponse.class);
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@Test
	@DisplayName("addExpense - create expense with valid category id ")
	public void addExpense_1() {
		String title = "t1"; String desc = "desc";
		Category category = Category.builder().title(title).description(desc).build();
		categoryRepository.save(category); assertThat(categoryRepository.findAll()).isNotNull();
		assertThat(categoryRepository.findAll().size()).isOne();
		ExpenseRequest request = ExpenseRequest.builder().title(title).description(desc).amount(1000L).effectiveDate(new Date()).categoryId(category.getId()).build();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		HttpEntity<ExpenseRequest> entity = new HttpEntity<>(request, headers);
		ResponseEntity<ExpenseResponse> response = restTemplate.exchange("/user/expense", HttpMethod.POST, entity, ExpenseResponse.class);
		assertThat(response).isNotNull(); assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull(); assertThat(response.getBody().getId()).isNotNull();
		assertThat(response.getBody().getTitle()).isNotNull();
		assertThat(response.getBody().getTitle()).isEqualTo(title);
		assertThat(response.getBody().getDescription()).isNotNull();
		assertThat(response.getBody().getDescription()).isEqualTo(desc);
		assertThat(response.getBody().getEffectiveDate()).isNotNull();
	}

	@Test
	@DisplayName("getExpense - get expense with wrong id")
	public void getExpense_0() {
		HttpHeaders headers = new HttpHeaders(); ; headers.setBearerAuth(accessToken);
		HttpEntity<ExpenseRequest> entity = new HttpEntity<>(headers);
		ResponseEntity<ExpenseResponse> response = restTemplate.exchange("/user/expense/1", HttpMethod.GET, entity, ExpenseResponse.class);
		assertThat(response).isNotNull(); assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	@DisplayName("getExpense - get expense with wrong id")
	public void getExpense_1() {
		String title = "t1"; String desc = "desc";
		Category category = Category.builder().title(title).description(desc).build();
		categoryRepository.save(category); assertThat(categoryRepository.findAll()).isNotNull();
		assertThat(categoryRepository.findAll().size()).isOne();

		Expense expense = Expense.builder().amount(1000L).title(title).description(desc).amount(1000L).category(category).effectiveDate(new Date()).build();
		expenseRepository.save(expense); assertThat(expenseRepository.findAll()).isNotNull();
		assertThat(expenseRepository.findAll().size()).isOne();

		var uri = "/user/expense/" + expense.getId();
		HttpHeaders headers = new HttpHeaders(); ;
		headers.setBearerAuth(accessToken);
		HttpEntity<ExpenseRequest> entity = new HttpEntity<>(headers);
		ResponseEntity<ExpenseResponse> response = restTemplate.exchange(uri, HttpMethod.GET, entity, ExpenseResponse.class);
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
	@DisplayName("getExpense - get expense report data based on two expense are on the same month and have same "
			+ "category but the other have different categories or are on different month ")
	public void getReport() {
		String title1 = "t1"; String desc1 = "desc";
		String title2 = "t2"; String desc2 = "desc2";
		Category category1 = Category.builder().title(title1).description(desc2).build();
		Category category2 = Category.builder().title(title2).description(desc2).build();
		categoryRepository.saveAll(List.of(category1, category2));

		Expense expense1 =
				Expense.builder().amount(1000L).title(title1).description(desc1).amount(1000L).category(category1).effectiveDate(new Date()).build();
		Expense expense2 =
				Expense.builder().amount(1000L).title(title1).description(desc1).amount(1000L).category(category1).effectiveDate(new Date()).build();

		Expense expense3 =
				Expense.builder().amount(1000L).title(title1).description(desc1).amount(1000L).category(category2).effectiveDate(new Date()).build();
		Expense expense4 =
				Expense.builder().amount(1000L).title(title2).description(desc2).amount(500L).category(category2).effectiveDate(new Date(getEpochMilliSecondsFromLocalDate(LocalDate.now().plusMonths(2)))).build();
		expenseRepository.saveAll(List.of(expense1, expense2, expense3, expense4));

		var uri = "/user/expense/report?pageNum=0&pageSize=10";
		HttpHeaders headers = new HttpHeaders(); ;
		headers.setBearerAuth(accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ExpenseRequest> entity = new HttpEntity<>(headers);
		ResponseEntity<ExpenseReportResponse> response = restTemplate.exchange(uri,
				HttpMethod.POST, entity,
				ExpenseReportResponse.class);

		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getData()).isNotNull();
		assertThat(response.getBody().getData().size()).isEqualTo(3);
		assertThat(response.getBody().getData().get(0).getTotalAmount()).isEqualTo(2000);
		assertThat(response.getBody().getData().get(1).getTotalAmount()).isEqualTo(1000);
		assertThat(response.getBody().getData().get(2).getTotalAmount()).isEqualTo(500);
		assertThat(response.getBody().getTotalElements()).isEqualTo(3);

	}


	public Long getEpochMilliSecondsFromLocalDate(LocalDate date) {
		return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

}
