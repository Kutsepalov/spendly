package com.acceleron.spendly.core.service.impl;

import com.acceleron.spendly.core.dto.RecordDto;
import com.acceleron.spendly.core.mapper.RecordMapper;
import com.acceleron.spendly.core.service.AuthenticationService;
import com.acceleron.spendly.core.service.RecordService;
import com.acceleron.spendly.persistence.dao.AccountHistoryDao;
import com.acceleron.spendly.persistence.dao.RecordDao;
import com.acceleron.spendly.persistence.entity.AccountHistory;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final RecordDao recordDao;
    private final AccountHistoryDao accountHistoryDao;

    private final RecordMapper recordMapper;
    private final AuthenticationService authenticationService;

    @Override
    public RecordDto findById(Long id) {
        return recordDao.findById(id)
                .map(recordMapper::toRecordDto)
                .orElse(null);
    }

    @Override
    public List<RecordDto> findAll() {
        return recordDao.findByUserId(authenticationService.getCurrentUserId())
                .stream()
                .map(recordMapper::toRecordDto).toList();
    }

    @Override
    public RecordDto save(RecordDto recordDto) {
        recordDto = recordMapper.toRecordDto(
                recordDao.save(recordMapper.toRecordEntity(recordDto, authenticationService.getCurrentUserId())));
        saveAccountBalanceChange(recordDto);
        return recordDto;
    }

    @Override
    public RecordDto update(RecordDto recordDto) {
        if (recordDto.getId() == null) {
            throw new IllegalIdentifierException("Record ID is required.");
        }
        return recordMapper.toRecordDto(
                recordDao.save(recordMapper.toRecordEntity(recordDto, authenticationService.getCurrentUserId())));
    }

    @Override
    public RecordDto delete(Long id) {
        return recordMapper.toRecordDto(
                recordDao.deleteRecord(id, authenticationService.getCurrentUserId()));
    }

    private void saveAccountBalanceChange(RecordDto recordDto) {
        switch (recordDto.getType()) {
            case INCOME -> applyIncome(recordDto);
            case EXPENSE -> applyExpense(recordDto);
            case TRANSFER -> applyTransfer(recordDto);
            default -> throw new IllegalArgumentException("Unknown record type: " + recordDto.getType());
        }
    }

    private void applyIncome(RecordDto recordDto) {
        AccountHistory accountHistory = AccountHistory.builder()
                .accountId(recordDto.getAccountId())
                .balance(accountHistoryDao.getAccountBalance(recordDto.getAccountId()).add(recordDto.getAmount()))
                .changeDatetime(Timestamp.from(OffsetDateTime.now(ZoneOffset.UTC).toInstant()))
                .build();
        accountHistoryDao.save(accountHistory);
    }

    private void applyExpense(RecordDto recordDto) {
        AccountHistory accountHistory = AccountHistory.builder()
                .accountId(recordDto.getAccountId())
                .balance(accountHistoryDao.getAccountBalance(recordDto.getAccountId()).subtract(recordDto.getAmount()))
                .changeDatetime(Timestamp.from(OffsetDateTime.now(ZoneOffset.UTC).toInstant()))
                .build();
        accountHistoryDao.save(accountHistory);
    }

    private void applyTransfer(RecordDto recordDto) {
        AccountHistory accountHistory = AccountHistory.builder()
                .accountId(recordDto.getAccountId())
                .balance(accountHistoryDao.getAccountBalance(recordDto.getAccountId()).subtract(recordDto.getAmount()))
                .changeDatetime(Timestamp.from(OffsetDateTime.now(ZoneOffset.UTC).toInstant()))
                .build();
        AccountHistory targetAccountHistory = AccountHistory.builder()
                .accountId(recordDto.getTargetAccountId())
                .balance(accountHistoryDao.getAccountBalance(recordDto.getTargetAccountId()).add(recordDto.getAmount()))
                .changeDatetime(Timestamp.from(OffsetDateTime.now(ZoneOffset.UTC).toInstant()))
                .build();
        accountHistoryDao.save(accountHistory);
        accountHistoryDao.save(targetAccountHistory);
    }
}
