package com.acceleron.spendly.controller;

import com.acceleron.spendly.core.dto.notification.NotificationDto;
import com.acceleron.spendly.core.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationDto>> getNotifications() {
        return ResponseEntity.ok(notificationService.getNotifications());
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<Void> acceptNotification(@PathVariable Long id) {
        notificationService.acceptNotification(id);
        return ResponseEntity.ok().build();
    }
}
