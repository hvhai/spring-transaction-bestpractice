package com.codehunter.springtransactionbestpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class TestSpringTransactionBestpracticeApplication {

	@Bean
	@ServiceConnection
	MySQLContainer<?> mysqlContainer() {
		return new MySQLContainer<>("mysql:latest")
				.withDatabaseName("testcontainer")
				.withUsername("root")
				.withPassword("root");
	}

	public static void main(String[] args) {
		SpringApplication.from(SpringTransactionBestpracticeApplication::main).with(TestSpringTransactionBestpracticeApplication.class).run(args);
	}

}
