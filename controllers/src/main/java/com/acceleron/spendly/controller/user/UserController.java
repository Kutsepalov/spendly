package com.acceleron.spendly.controller.user;

import com.acceleron.spendly.core.dto.UserDto;
import com.acceleron.spendly.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDto> getUserDetails(){
        return ResponseEntity.ok(userService.getCurrentUser().orElseThrow(() -> new RuntimeException("User not found"))); //TODO: change exception
    }
}
