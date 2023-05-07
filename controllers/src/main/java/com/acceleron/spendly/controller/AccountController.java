package com.acceleron.spendly.controller;

import com.acceleron.spendly.core.dto.AccountDto;
import com.acceleron.spendly.core.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{id}")
    public AccountDto getAccount(@PathVariable UUID id) {
        return accountService.findById(id);
    }

    @GetMapping
    public List<AccountDto> getAccounts() {
        return accountService.findAll();
    }

    @PostMapping
    public AccountDto createAccount(@RequestBody AccountDto account) {
        return accountService.save(account);
    }

    @PutMapping
    public AccountDto updateAccount(@RequestBody AccountDto account) {
        return accountService.update(account);
    }

    @DeleteMapping("/{id}")
    public AccountDto deleteAccount(@PathVariable UUID id) {
        return accountService.delete(id);
    }
}
