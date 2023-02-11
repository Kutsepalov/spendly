package com.acceleron.spendly.core.service;

import com.acceleron.spendly.core.dto.AccountDto;

import java.util.List;
import java.util.UUID;

public interface AccountService extends CrudSevice<AccountDto, UUID> {

    List<AccountDto> findAll();
}
