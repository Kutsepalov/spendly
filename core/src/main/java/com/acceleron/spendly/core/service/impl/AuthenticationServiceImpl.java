package com.acceleron.spendly.core.service.impl;

import com.acceleron.spendly.core.dto.UserDto;
import com.acceleron.spendly.core.service.AuthenticationService;
import com.acceleron.spendly.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.findByUsername(username).map(UserDto::getId)
                .orElseThrow(() -> new IllegalArgumentException("User with username" + username + "not found"));
    }

    public UUID registerUser(UserDto userDto) {
        return userService.save(userDto).getId();
    }

    @Override
    public void authenticateUser(UsernamePasswordAuthenticationToken token) {
        authenticationManager.authenticate(token);
    }
}
