package com.codehunter.springtransactionbestpractice.respository;

import com.codehunter.springtransactionbestpractice.entity.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, UUID> {
//    @Lock(LockModeType.OPTIMISTIC)
    @Query(value = """
            SELECT balance
            FROM Account
            WHERE iban = :iban
            """, nativeQuery = false)
    long getBalance(@Param("iban") String iban);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query(value = """
            UPDATE Account 
            SET balance = balance + :cents
            WHERE iban = :iban
            """, nativeQuery = false)
    @Modifying
    @Transactional
    int addBalance(@Param("iban") String iban, @Param("cents") long cents);
}
