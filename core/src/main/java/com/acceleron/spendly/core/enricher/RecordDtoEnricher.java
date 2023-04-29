package com.acceleron.spendly.core.enricher;

import com.acceleron.spendly.core.dto.RecordDto;
import com.acceleron.spendly.core.service.AccountService;
import com.acceleron.spendly.core.service.currency.CurrencyConvertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordDtoEnricher implements Enricher<RecordDto> { // TODO refactor this enricher to use strategy pattern for each record type

    private final AccountService accountService;
    private final CurrencyConvertService convertService;

    @Override
    public RecordDto enrich(RecordDto recordDto) {
        log.debug("Enriching record: {}", recordDto);
        enrichCurrencyField(recordDto);
        enrichTargetAccountIdField(recordDto);
        enrichTargetCurrencyField(recordDto);
        enrichTargetAmountField(recordDto);
        enrichCreationDatetime(recordDto);
        log.debug("Enriched record: {}", recordDto);
        return recordDto;
    }

    private void enrichCurrencyField(RecordDto recordDto) {
        if(Objects.isNull(recordDto.getCurrency())) {
            recordDto.setCurrency(accountService.getCurrency(recordDto.getAccountId()));
            log.debug("Currency was not set, set to account currency: {}", recordDto.getCurrency());
        }
    }

    private void enrichTargetAccountIdField(RecordDto recordDto) {
        if(Objects.isNull(recordDto.getTargetAccountId()) && recordDto.getType().isTransfer()) {
            recordDto.setTargetAccountId(recordDto.getAccountId());
            log.debug("Target account id was not set, set to account id: {}", recordDto.getAccountId());
        } else if(Objects.isNull(recordDto.getTargetAccountId())) {
            log.error("Target account id was not set, but record type is transfer");
            throw new IllegalArgumentException("Target account id is required for transfer record type");
        }
    }

    private void enrichTargetCurrencyField(RecordDto recordDto) {
        if(Objects.isNull(recordDto.getTargetCurrency()) && recordDto.getType().isTransfer()) {
            recordDto.setTargetCurrency(accountService.getCurrency(recordDto.getAccountId()));
            log.debug("Target currency was not set, set to account currency: {}", recordDto.getTargetCurrency());
        } else if(Objects.isNull(recordDto.getTargetCurrency())) {
            recordDto.setTargetCurrency(accountService.getCurrency(recordDto.getTargetAccountId()));
            log.debug("Target currency was not set, set to target account currency: {}", recordDto.getTargetCurrency());
        }
    }

    private void enrichTargetAmountField(RecordDto recordDto) {
        if(Objects.isNull(recordDto.getTargetAmount())) {
            if(recordDto.getTargetCurrency().equals(recordDto.getCurrency())) {
                recordDto.setTargetAmount(recordDto.getAmount());
                log.debug("Target amount was not set, set to default amount: {}", recordDto.getAmount());
            } else {
                recordDto.setTargetAmount(convertService
                        .converter(recordDto.getCurrency(), recordDto.getTargetCurrency())
                        .apply(recordDto.getAmount()));
                log.debug("Target amount was not set, convert amount to target currency: amount: {}, currency: {}",
                        recordDto.getTargetAmount(), recordDto.getTargetCurrency());
            }
        }
    }

    private void enrichCreationDatetime(RecordDto recordDto) {
        if(Objects.isNull(recordDto.getCreationDatetime())) {
            recordDto.setCreationDatetime(ZonedDateTime.now(ZoneOffset.UTC));
            log.debug("Creation datetime was not set, set to current datetime: {}", recordDto.getCreationDatetime());
        }
    }
}
