package com.acceleron.spendly.core.service;

import com.acceleron.spendly.core.dto.UserDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.UUID;

public interface AuthenticationService {

    UUID getCurrentUserId();
    UUID registerUser(UserDto userDto);

    void authenticateUser(UsernamePasswordAuthenticationToken token);
}
