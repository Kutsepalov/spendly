package com.acceleron.spendly.core.service;

import com.acceleron.spendly.core.dto.AccountDto;
import com.acceleron.spendly.persistence.entity.enums.CurrencyCode;

import java.util.List;
import java.util.UUID;

public interface AccountService extends CrudService<AccountDto, UUID> {

    List<AccountDto> findAll();

    CurrencyCode getCurrency(UUID accountId);
}
