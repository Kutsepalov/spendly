package com.acceleron.spendly.core.service.impl;

import com.acceleron.spendly.core.dto.UserDto;
import com.acceleron.spendly.core.dto.auth.AuthenticationRequest;
import com.acceleron.spendly.core.service.AuthenticationService;
import com.acceleron.spendly.core.service.UserService;
import com.acceleron.spendly.core.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final UserDetailsService userDetailsService;
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
    public String authenticateUser(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(authenticationRequest.mapToAuthenticationToken());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsernameOrEmail());
        if (Objects.nonNull(userDetails)){
            return jwtUtils.generateToken(userDetails);
        } else {
            throw new IllegalArgumentException("User is not exists");
        }
    }
}
