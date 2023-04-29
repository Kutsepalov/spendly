package com.acceleron.spendly.controller.auth;

import com.acceleron.spendly.core.dto.auth.AuthenticationRequest;
import com.acceleron.spendly.core.dto.auth.RegistrationRequest;
import com.acceleron.spendly.core.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticateUser(request));
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest request) {
        return ResponseEntity.ok("User registered successfully: " +
                authenticationService.registerUser(request.mapToUserDto()));
    }
}
