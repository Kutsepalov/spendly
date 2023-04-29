package com.acceleron.spendly.core.service.impl;

import com.acceleron.spendly.core.dto.RecordDto;
import com.acceleron.spendly.core.service.AccountUpdateService;
import com.acceleron.spendly.persistence.dao.AccountHistoryDao;
import com.acceleron.spendly.persistence.entity.AccountHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountUpdateServiceImpl implements AccountUpdateService {

    private final AccountHistoryDao accountHistoryDao;

    @Override
    @Transactional
    public void changeAccountBalance(RecordDto recordDto) {
        saveAccountBalanceChange(recordDto);
    }

    @Override
    public void deleteChangings(RecordDto deletedRecordDto) {
        accountHistoryDao.deleteByRecordId(deletedRecordDto.getId());
    }

    //TODO: implement updateAccountBalance method

    private void saveAccountBalanceChange(RecordDto recordDto) {
        switch (recordDto.getType()) {
            case INCOME -> applyIncome(recordDto);
            case EXPENSE -> applyExpense(recordDto);
            case TRANSFER -> applyTransfer(recordDto);
            default -> throw new IllegalArgumentException("Unknown record type: " + recordDto.getType());
        }
    }

    private void applyIncome(RecordDto recordDto) {
        BigDecimal accountBalance = accountHistoryDao.getAccountBalance(recordDto.getTargetAccountId()).add(recordDto.getTargetAmount());
        accountHistoryDao.save(targetAccountHistory(recordDto, accountBalance));
    }

    private void applyExpense(RecordDto recordDto) {
        BigDecimal accountBalance = accountHistoryDao.getAccountBalance(recordDto.getTargetAccountId()).subtract(recordDto.getTargetAmount());
        accountHistoryDao.save(targetAccountHistory(recordDto, accountBalance));
    }

    private void applyTransfer(RecordDto recordDto) {
        BigDecimal sourceAccountBalance = accountHistoryDao.getAccountBalance(recordDto.getAccountId()).subtract(recordDto.getAmount());
        BigDecimal targetAccountBalance = accountHistoryDao.getAccountBalance(recordDto.getTargetAccountId()).add(recordDto.getTargetAmount());

        accountHistoryDao.save(sourceAccountHistory(recordDto, sourceAccountBalance));
        accountHistoryDao.save(targetAccountHistory(recordDto, targetAccountBalance));
    }
    private AccountHistory sourceAccountHistory(RecordDto recordDto, BigDecimal balance) {
        return mapToAccountHistory(recordDto, recordDto.getAccountId(), balance);
    }

    private AccountHistory targetAccountHistory(RecordDto recordDto, BigDecimal balance) {
        return mapToAccountHistory(recordDto, recordDto.getTargetAccountId(), balance);
    }

    private AccountHistory mapToAccountHistory(RecordDto recordDto, UUID accountId, BigDecimal balance) {
        return AccountHistory.builder()
                .accountId(accountId)
                .balance(balance)
                .changeDatetime(Timestamp.from(recordDto.getCreationDatetime().toInstant()))
                .recordId(recordDto.getId())
                .build();
    }
}
