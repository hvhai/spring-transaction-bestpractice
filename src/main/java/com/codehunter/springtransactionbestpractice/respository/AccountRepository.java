package com.codehunter.springtransactionbestpractice.respository;

import com.codehunter.springtransactionbestpractice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, UUID> {
    @Query(value = """
            SELECT balance
            FROM account
            WHERE iban = :iban
            """,
            nativeQuery = true)
    long getBalance(@Param("iban") String iban);

    @Query(value = """
            UPDATE account
            SET balance = balance + :cents
            WHERE iban = :iban
            """,
            nativeQuery = true)
    @Modifying
    @Transactional
    int addBalance(@Param("iban") String iban, @Param("cents") long cents);
}
