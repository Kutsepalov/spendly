package com.acceleron.spendly.core.service;

import com.acceleron.spendly.core.dto.notification.NotificationDto;

import java.util.List;

public interface NotificationService {

    void markAsRead(Long id);
    NotificationDto findById(Long id);
    List<NotificationDto> getNotifications();
    void sendNotification(NotificationDto notificationDto);
    void acceptNotification(Long id);
}
