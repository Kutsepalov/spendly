package com.acceleron.spendly.core.service;

import com.acceleron.spendly.core.dto.RecordDto;

public interface AccountUpdateService {

    void changeAccountBalance(RecordDto recordDto);

    void deleteChangings(RecordDto deletedRecordDto);
}
