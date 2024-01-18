package com.example.fintech.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
	@Bean
	public OpenAPI usersMicroserviceOpenAPI() {
		return new OpenAPI().info(new Info().title("Exercise Api").description("Just created review service with the help of search api").version("1.0"));
	}
}