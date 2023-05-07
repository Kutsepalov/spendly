package com.acceleron.spendly.core.service.impl;

import com.acceleron.spendly.core.dto.notification.NotificationDto;
import com.acceleron.spendly.core.dto.notification.data.GroupInvitation;
import com.acceleron.spendly.core.mapper.NotificationMapper;
import com.acceleron.spendly.core.service.AuthenticationService;
import com.acceleron.spendly.core.service.NotificationService;
import com.acceleron.spendly.core.service.UserGroupService;
import com.acceleron.spendly.persistence.dao.NotificationDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationDao notificationDao;
    private final UserGroupService userGroupService;
    private final NotificationMapper notificationMapper;
    private final AuthenticationService authenticationService;

    @Override
    public NotificationDto findById(Long id) {
        return notificationDao.findByIdAndReceiverId(id, authenticationService.getCurrentUserId()).map(notificationMapper::toDto).orElse(null);
    }

    @Override
    public void markAsRead(Long id) {
        NotificationDto notification = findById(id);
        if (Objects.nonNull(notification)) {
            notification.setRead(true);
        }
        notificationDao.save(notificationMapper.toEntity(notification));
    }

    @Override
    public List<NotificationDto> getNotifications() {
        return notificationDao.findAllByReceiverId(authenticationService.getCurrentUserId()).stream()
                .map(notificationMapper::toDto).toList();
    }

    @Override
    public void sendNotification(NotificationDto notificationDto) {
        notificationDao.save(notificationMapper.toEntity(notificationDto, authenticationService.getCurrentUserId()));
    }

    @Override
    public void acceptNotification(Long id) {
        NotificationDto notification = findById(id);
        if (Objects.nonNull(notification)) {
            GroupInvitation groupInvitation = (GroupInvitation) notification.getData();
            groupInvitation.getGroup().getUserIds().add(authenticationService.getCurrentUserId());
            groupInvitation.setAccepted(true);
            notification.setRead(true);
            userGroupService.enterGroup(groupInvitation.getGroup().getId());
        }
        notificationDao.save(notificationMapper.toEntity(notification));
    }
}
