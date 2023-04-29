package com.acceleron.spendly.core.service.currency;

import com.acceleron.spendly.persistence.entity.enums.CurrencyCode;

import java.math.BigDecimal;
import java.util.function.Function;

public interface CurrencyConvertService {

    Function<BigDecimal, BigDecimal> converter(CurrencyCode sourceCurrency, CurrencyCode targetCurrency);
}
