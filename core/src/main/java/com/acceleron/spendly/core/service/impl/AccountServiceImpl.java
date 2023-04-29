package com.acceleron.spendly.core.service.impl;

import com.acceleron.spendly.core.dto.AccountDto;
import com.acceleron.spendly.core.mapper.AccountMapper;
import com.acceleron.spendly.core.service.AccountService;
import com.acceleron.spendly.core.service.AuthenticationService;
import com.acceleron.spendly.persistence.dao.AccountDao;
import com.acceleron.spendly.persistence.entity.Account;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;
    private final AccountMapper accountMapper;
    private final AuthenticationService authenticationService;

    @Override
    public AccountDto findById(UUID id) {
        return accountDao.findById(id)
                .map(accountMapper::toAccountDto)
                .orElse(null);
    }

    @Override
    public List<AccountDto> findAll() {
        return accountDao.findByUserId(authenticationService.getCurrentUserId())
                .stream()
                .map(accountMapper::toAccountDto).toList();
    }

    @Override
    public AccountDto save(AccountDto account) {
        return accountMapper.toAccountDto(
                accountDao.save(accountMapper.toAccountEntity(account, authenticationService.getCurrentUserId()))
        );
    }

    @Override
    public AccountDto update(AccountDto accountDto) {
        if (accountDto.getId() == null) {
            throw new IllegalIdentifierException("Account ID is required.");
        }
        Account account = accountMapper.toAccountEntity(accountDto, authenticationService.getCurrentUserId());
        return accountMapper.toAccountDto(accountDao.save(account));
    }

    @Override
    public AccountDto delete(UUID accountId) {
        return accountMapper.toAccountDto(accountDao.deleteAccount(accountId, authenticationService.getCurrentUserId()));
    }
}
