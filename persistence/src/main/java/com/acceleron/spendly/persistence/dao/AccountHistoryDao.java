package com.acceleron.spendly.persistence.dao;

import com.acceleron.spendly.persistence.entity.Account;
import com.acceleron.spendly.persistence.entity.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface AccountHistoryDao extends JpaRepository<AccountHistory, Long> {

    @Query(nativeQuery = true,
          value = "SELECT COALESCE((SELECT AH.AMOUNT FROM " + AccountHistory.TABLE_NAME + " AH " +
                  "INNER JOIN " + Account.TABLE_NAME + " A ON AH.ACCOUNT_ID = A.ID " +
                  "WHERE AH.ACCOUNT_ID = :accountId " +
                  "ORDER BY AH.CHANGE_DATETIME DESC LIMIT 1), 0)")
    BigDecimal getAccountBalance(UUID accountId);
}