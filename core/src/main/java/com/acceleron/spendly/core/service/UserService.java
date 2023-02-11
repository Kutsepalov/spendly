package com.acceleron.spendly.core.service;

import com.acceleron.spendly.core.dto.UserDto;

import java.util.Optional;
import java.util.UUID;

public interface UserService extends CrudSevice<UserDto, UUID> {

    Optional<UserDto> findByUsernameOrEmail(String usernameOrEmail);
    Optional<UserDto> findByUsername(String username);
}
