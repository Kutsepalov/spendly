package com.acceleron.spendly.core.mapper;

import com.acceleron.spendly.core.dto.RecordDto;
import com.acceleron.spendly.persistence.entity.Record;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RecordMapper {

    @Mapping(source = "creationDatetime", target = "creationDatetime", qualifiedByName = "mapToZonedDateTime")
    RecordDto toRecordDto(Record model);

    @Mapping(source = "creationDatetime", target = "creationDatetime", qualifiedByName = "mapToTimestamp")
    Record toRecordEntity(RecordDto model);
    @Mapping(source = "dto.creationDatetime", target = "creationDatetime", qualifiedByName = "mapToTimestamp")
    @Mapping(source = "userId", target = "userId")
    Record toRecordEntity(RecordDto dto, UUID userId);

    @Named("mapToZonedDateTime")
    default ZonedDateTime mapToZonedDateTime(Timestamp creationDatetime) {
        LocalDateTime localDateTime = creationDatetime.toLocalDateTime();
        return localDateTime.atZone(ZoneOffset.UTC);
    }

    @Named("mapToTimestamp")
    default Timestamp mapToTimestamp(ZonedDateTime creationDatetime) {
        return Timestamp.valueOf(creationDatetime.toLocalDateTime());
    }
}
