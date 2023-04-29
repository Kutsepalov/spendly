package com.acceleron.spendly.core.service.impl;

import com.acceleron.spendly.core.dto.RecordDto;
import com.acceleron.spendly.core.enricher.RecordDtoEnricher;
import com.acceleron.spendly.core.mapper.RecordMapper;
import com.acceleron.spendly.core.service.AuthenticationService;
import com.acceleron.spendly.core.service.RecordService;
import com.acceleron.spendly.persistence.dao.RecordDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final RecordDao recordDao;
    private final RecordMapper recordMapper;
    private final RecordDtoEnricher recordDtoEnricher;
    private final AuthenticationService authenticationService;
    private final AccountUpdateServiceImpl accountUpdateService;

    @Override
    public RecordDto findById(Long id) {
        return recordDao.findById(id)
                .map(recordMapper::toRecordDto)
                .orElse(null);
    }

    @Override
    public List<RecordDto> findAll() {
        return recordDao.findAll(authenticationService.getCurrentUserId())
                .stream()
                .map(recordMapper::toRecordDto).toList();
    }

    @Override
    public List<RecordDto> findByAccountId(UUID accountId) {
        return recordDao.findByAccountId(accountId, authenticationService.getCurrentUserId())
                .stream()
                .map(recordMapper::toRecordDto).toList();
    }

    @Override
    public RecordDto save(RecordDto recordDto) {
        recordDtoEnricher.enrich(recordDto);

        recordDto = recordMapper.toRecordDto(
                recordDao.save(recordMapper.toRecordEntity(recordDto, authenticationService.getCurrentUserId())));
        log.debug("Record saved: {}", recordDto);

        accountUpdateService.changeAccountBalance(recordDto);
        return recordDto;
    }

    @Override
    public RecordDto update(RecordDto recordDto) {
        recordDtoEnricher.enrich(recordDto);
        if (recordDto.getId() == null) {
            throw new IllegalIdentifierException("Record ID is required.");
        }
        recordDto = recordMapper.toRecordDto(
                recordDao.save(recordMapper.toRecordEntity(recordDto, authenticationService.getCurrentUserId())));
        log.debug("Record updated: {}", recordDto);
        return recordDto;
    }

    @Override
    @Transactional
    public RecordDto delete(Long id) {
        RecordDto deletedRecordDto = findById(id);
        accountUpdateService.deleteChangings(deletedRecordDto);
        recordDao.deleteRecord(id, authenticationService.getCurrentUserId());
        return deletedRecordDto;
    }
}
