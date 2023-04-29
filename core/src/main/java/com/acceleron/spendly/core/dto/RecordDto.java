package com.acceleron.spendly.core.dto;

import com.acceleron.spendly.persistence.entity.enums.RecordType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private BigDecimal amount;
    private String currency;

    private UUID targetAccountId;
    private BigDecimal targetAmount;
    private String targetCurrency;

    private String note;
    private String payee;
    private RecordType type;

    private CategoryDto category;

    private UUID accountId;
    private UUID userId;

    private ZonedDateTime creationDatetime;
}
