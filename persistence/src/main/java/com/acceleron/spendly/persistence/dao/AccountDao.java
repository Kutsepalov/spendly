package com.acceleron.spendly.persistence.dao;

import com.acceleron.spendly.persistence.entity.Account;
import com.acceleron.spendly.persistence.entity.enums.CurrencyCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountDao extends JpaRepository<Account, UUID> {

    @Query(nativeQuery = true,
            value = "SELECT ACCOUNTS.* FROM ACCOUNTS " +
                    "INNER JOIN USERS ON ACCOUNTS.USER_ID = USERS.ID " +
                    "WHERE USERS.USERNAME = :username")
    List<Account> findByUsername(@Param("username") String username);
    List<Account> findByUserId(UUID id);

    @Query(nativeQuery = true,
            value = "DELETE FROM ACCOUNTS " +
                    "WHERE ACCOUNTS.ID = :id AND ACCOUNTS.USER_ID = :userId")
    @Modifying
    Account deleteAccount(UUID id, UUID userId);

    @Query(nativeQuery = true,
            value = "SELECT ACCOUNTS.CURRENCY FROM ACCOUNTS " +
                    "WHERE ACCOUNTS.ID = :accountId AND ACCOUNTS.USER_ID = :userId")
    CurrencyCode getCurrency(UUID accountId, UUID userId);
}