package com.acceleron.spendly.core.mapper;

import com.acceleron.spendly.core.dto.AccountDto;
import com.acceleron.spendly.persistence.entity.Account;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDto toAccountDto(Account model);
    Account toAccountEntity(AccountDto model);
    Account toAccountEntity(AccountDto model, UUID userId);
}
