package com.acceleron.spendly.core.service;

import com.acceleron.spendly.core.dto.UserDataDto;

import java.util.Optional;
import java.util.UUID;

public interface UserService extends CrudService<UserDataDto, UUID> {

    Optional<UserDataDto> getCurrentUser();
    Optional<UserDataDto> findByUsernameOrEmail(String usernameOrEmail);
    Optional<UserDataDto> findByUsername(String username);
}
