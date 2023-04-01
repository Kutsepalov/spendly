package com.acceleron.spendly.core.dto.auth;

import com.acceleron.spendly.core.dto.UserDto;
import lombok.Data;

@Data
public class RegistrationRequest {

    private String name;
    private String username;
    private String email;
    private String password;

    public UserDto mapToUserDto() {
        return UserDto.builder()
                .name(name)
                .username(username)
                .email(email)
                .password(password)
                .build();
    }
}
