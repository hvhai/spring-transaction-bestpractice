package com.codehunter.springtransactionbestpractice;

import com.codehunter.springtransactionbestpractice.respository.AccountRepository;
import com.codehunter.springtransactionbestpractice.service.TransferServiceImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransferIntegrationTest extends MySqlContainerBaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransferIntegrationTest.class);
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransferServiceImpl transferService;

    @Test
    void testParallelExecution() throws InterruptedException {
        assertEquals(10, accountRepository.getBalance("alice-456"));
        assertEquals(0, accountRepository.getBalance("bob-123"));
        int threadCount = 5;
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                try {
                    startLatch.await();

                    transferService.transfer(
                            "alice-456", "bob-123", 5L
                    );
                } catch (Exception e) {
                    LOGGER.error("Transfer failed", e);
                } finally {
                    endLatch.countDown();
                }
            }).start();
        }
        startLatch.countDown();
        endLatch.await();
        LOGGER.info(
                "Alice's balance {}",
                accountRepository.getBalance("alice-456")
        );
        LOGGER.info(
                "Bob's balance {}",
                accountRepository.getBalance("bob-123")
        );
    }
}
