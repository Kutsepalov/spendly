package com.acceleron.spendly.controller.record;

import com.acceleron.spendly.core.dto.RecordDto;
import com.acceleron.spendly.core.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/records")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @GetMapping("/{id}")
    public RecordDto getRecord(@PathVariable Long id) {
        return recordService.findById(id);
    }

    @GetMapping
    public List<RecordDto> getRecords() {
        return recordService.findAll();
    }

    @GetMapping("/account/{accountId}")
    public List<RecordDto> getRecordsByAccount(@PathVariable UUID accountId) {
        return recordService.findByAccountId(accountId);
    }

    @PostMapping
    public RecordDto createRecord(@RequestBody RecordDto recordDto) {
        return recordService.save(recordDto);
    }

    @PutMapping
    public RecordDto updateRecord(@RequestBody RecordDto recordDto) {
        return recordService.update(recordDto);
    }

    @DeleteMapping("/{id}")
    public RecordDto deleteRecord(@PathVariable Long id) {
        return recordService.delete(id);
    }
}
