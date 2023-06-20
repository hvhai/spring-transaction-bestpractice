package com.codehunter.springtransactionbestpractice.service;

import com.codehunter.springtransactionbestpractice.respository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl {
    private final AccountRepository accountRepository;

    @Transactional
//    @Transactional(isolation = Isolation.SERIALIZABLE)
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
