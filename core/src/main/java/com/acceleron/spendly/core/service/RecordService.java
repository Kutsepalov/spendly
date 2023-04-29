package com.acceleron.spendly.core.service;

import com.acceleron.spendly.core.dto.RecordDto;

import java.util.List;
import java.util.UUID;

public interface RecordService extends CrudService<RecordDto, Long> {

    List<RecordDto> findAll();

    List<RecordDto> findByAccountId(UUID accountId);
}
