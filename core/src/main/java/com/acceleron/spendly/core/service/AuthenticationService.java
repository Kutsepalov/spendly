package com.acceleron.spendly.core.service;

import com.acceleron.spendly.core.dto.UserDataDto;
import com.acceleron.spendly.core.dto.auth.AuthenticationRequest;

import java.util.UUID;

public interface AuthenticationService {

    UUID getCurrentUserId();
    UUID registerUser(UserDataDto userDataDto);

    String authenticateUser(AuthenticationRequest request);
}
