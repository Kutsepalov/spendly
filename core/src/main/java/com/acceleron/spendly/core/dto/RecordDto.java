package com.acceleron.spendly.core.dto;

import com.acceleron.spendly.persistence.entity.enums.CurrencyCode;
import com.acceleron.spendly.persistence.entity.enums.RecordType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RecordDto {

    private Long id;

    @NonNull
    private UUID accountId;
    @NonNull
    private BigDecimal amount;
    private CurrencyCode currency;

    @NonNull
    private RecordType type;
    @NonNull
    private UUID categoryId;

    private UUID targetAccountId;
    private BigDecimal targetAmount;
    private CurrencyCode targetCurrency;

    private String note;
    private String payee;

    private ZonedDateTime creationDatetime;
}
