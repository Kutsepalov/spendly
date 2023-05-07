package com.acceleron.spendly.core.mapper;

import com.acceleron.spendly.core.dto.notification.NotificationDto;
import com.acceleron.spendly.core.dto.notification.NotificationDto.NotificationType;
import com.acceleron.spendly.core.dto.notification.data.NotificationData;
import com.acceleron.spendly.persistence.entity.Notification;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface NotificationMapper extends EntityMapper<Notification, NotificationDto>{

    Notification toEntity(NotificationDto dto, UUID senderId);

    @Mapping(source = "read", target = "isRead")
    @Mapping(source = "type", target = "type")
    @Mapping(target = "data", expression = "java(mapDataFromJson(entity.getData(), entity.getType()))")
    NotificationDto toDto(Notification entity);

    @SneakyThrows
    default String map(NotificationData data) {
        return new ObjectMapper().writeValueAsString(data);
    }

    @SneakyThrows
    default NotificationData mapDataFromJson(String json, String type) {
        return new ObjectMapper().readValue(json, Enum.valueOf(NotificationType.class, type).getDataClass());
    }
}
