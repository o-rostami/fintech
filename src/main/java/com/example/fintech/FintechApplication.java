package com.example.fintech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class FintechApplication {

	public static void main(String[] args) {
		SpringApplication.run(FintechApplication.class, args);
	}

}
