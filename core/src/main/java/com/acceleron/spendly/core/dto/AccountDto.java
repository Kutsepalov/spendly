package com.acceleron.spendly.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private UUID id;
    private String name;
    private String type;
    private BigDecimal amount;
    private String currency;
    private String color;
}
