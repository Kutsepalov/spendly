package com.acceleron.spendly.core.service;

import com.acceleron.spendly.core.dto.UserDto;
import com.acceleron.spendly.core.dto.auth.AuthenticationRequest;

import java.util.UUID;

public interface AuthenticationService {

    UUID getCurrentUserId();
    UUID registerUser(UserDto userDto);

    String authenticateUser(AuthenticationRequest request);
}
