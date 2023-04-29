package com.acceleron.spendly.core.service.currency;

import com.acceleron.spendly.persistence.entity.enums.CurrencyCode;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;

@Service
public class CurrencyConvertServiceImpl implements CurrencyConvertService {

    private static final int DECIMAL_PLACES = 2;

    @Override
    @Cacheable("converters")
    public Function<BigDecimal, BigDecimal> converter(CurrencyCode sourceCurrency, CurrencyCode targetCurrency) {
        return sourceAmount -> sourceAmount.multiply(getQuotedPrice(sourceCurrency, targetCurrency))
                .setScale(DECIMAL_PLACES, RoundingMode.FLOOR);
    }

    private BigDecimal getQuotedPrice(CurrencyCode sourceCurrency, CurrencyCode targetCurrency) {
        try {
            return YahooFinance.getFx(yahooSymbol(sourceCurrency, targetCurrency)).getPrice();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private String yahooSymbol(CurrencyCode sourceCurrency, CurrencyCode targetCurrency) {
        return sourceCurrency.name() + targetCurrency.name() + "=X";
    }
}
