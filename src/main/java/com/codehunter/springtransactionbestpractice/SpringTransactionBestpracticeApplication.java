package com.codehunter.springtransactionbestpractice;

import com.codehunter.springtransactionbestpractice.entity.Account;
import com.codehunter.springtransactionbestpractice.respository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
@Slf4j
public class SpringTransactionBestpracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringTransactionBestpracticeApplication.class, args);
	}

	@Bean
	ApplicationRunner initData(AccountRepository accountRepository) {
		return arg -> {
			accountRepository.save(Account.builder().name("Bob").iban("bob-123").balance(BigDecimal.ZERO).build());
			accountRepository.save(Account.builder().name("Alice").iban("alice-456").balance(BigDecimal.valueOf(10)).build());
			accountRepository.findAll().stream().forEach(account -> log.info("Account data : {}", account));
		};
	}
}
