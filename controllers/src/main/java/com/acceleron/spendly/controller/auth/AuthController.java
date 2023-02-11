package com.acceleron.spendly.controller.auth;

import com.acceleron.spendly.controller.dto.auth.AuthenticationRequest;
import com.acceleron.spendly.controller.dto.auth.RegistrationRequest;
import com.acceleron.spendly.core.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-in")
    public ResponseEntity<String> authenticateUser(@RequestBody AuthenticationRequest request){
        authenticationService.authenticateUser(request.mapToAuthenticationToken());
        return new ResponseEntity<>("User signed-in successfully!", HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest request) {
        UUID userId = authenticationService.registerUser(request.mapToUserDto());
        return new ResponseEntity<>("User registered successfully: " + userId, HttpStatus.OK);
    }
}
