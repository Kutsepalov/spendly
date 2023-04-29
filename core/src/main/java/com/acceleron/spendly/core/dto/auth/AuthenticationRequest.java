package com.acceleron.spendly.core.dto.auth;

import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
public class AuthenticationRequest {

    private String usernameOrEmail;
    private String password;

    public UsernamePasswordAuthenticationToken mapToAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(usernameOrEmail, password);
    }
}
