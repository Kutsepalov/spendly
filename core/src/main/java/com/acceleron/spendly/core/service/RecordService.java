package com.acceleron.spendly.core.service;

import com.acceleron.spendly.core.dto.RecordDto;

import java.util.List;

public interface RecordService extends CrudService<RecordDto, Long> {

    List<RecordDto> findAll();
}
