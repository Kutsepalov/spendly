package com.acceleron.spendly.core.dto.auth;

import com.acceleron.spendly.core.dto.UserDataDto;
import lombok.Data;

@Data
public class RegistrationRequest {

    private String name;
    private String surname;
    private String currency;
    private String email;
    private String username;
    private String password;

    public UserDataDto mapToUserDto() {
        return UserDataDto.builder()
                .name(name)
                .surname(surname)
                .currency(currency)
                .email(email)
                .username(username)
                .password(password)
                .build();
    }
}
