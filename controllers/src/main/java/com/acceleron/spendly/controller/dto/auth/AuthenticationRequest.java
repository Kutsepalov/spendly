package com.acceleron.spendly.controller.dto.auth;

import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static com.acceleron.spendly.core.utils.UserAuthenticationMatcher.isEmail;
import static com.acceleron.spendly.core.utils.UserAuthenticationMatcher.isUsername;

@Data
public class AuthenticationRequest {

    private String email;
    private String username;
    private String password;

    public UsernamePasswordAuthenticationToken mapToAuthenticationToken() {
        String usernameOrEmail = recognizeUsernameOrEmail();
        return new UsernamePasswordAuthenticationToken(usernameOrEmail, password);
    }

    private String recognizeUsernameOrEmail() {
        if (email != null && isEmail(email)) {
            return email;
        }
        else if (username != null && isUsername(username)) {
            return username;
        }
        else {
            throw new IllegalArgumentException("Invalid username or email");
        }
    }
}
