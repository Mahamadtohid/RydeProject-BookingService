package com.example.RydeBookingService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EntityScan(basePackages = "com.example.RydeProject_EntityService.models")
@EnableJpaRepositories(basePackages = "com.example.RydeBookingService.repositories")
public class RydeBookingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RydeBookingServiceApplication.class, args);
	}


	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
