package com.codehunter.springtransactionbestpractice.service;

import com.codehunter.springtransactionbestpractice.respository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl {
    private final AccountRepository accountRepository;

    public boolean transfer(String fromIban, String toIban, long cents) {
        boolean status = true;
        long fromBalance = accountRepository.getBalance(fromIban);

        if (fromBalance >= cents) {
            status &= accountRepository.addBalance(
                    fromIban, (-1) * cents
            ) > 0;

            status &= accountRepository.addBalance(
                    toIban, cents
            ) > 0;
        }

        return status;
    }
}
