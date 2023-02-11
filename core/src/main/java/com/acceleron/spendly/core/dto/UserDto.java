package com.acceleron.spendly.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private UUID id;
    private String name;
    private String username;
    private String email;
    private String password;
}
