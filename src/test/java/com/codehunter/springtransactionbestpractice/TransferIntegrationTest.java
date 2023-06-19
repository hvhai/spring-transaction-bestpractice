package com.codehunter.springtransactionbestpractice;

import com.codehunter.springtransactionbestpractice.respository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransferIntegrationTest extends MySqlContainerBaseTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    void testParallelExecution() throws InterruptedException{
        assertEquals(10, accountRepository.getBalance("alice-456"));
        assertEquals(0, accountRepository.getBalance("bob-123"));
    }
}
