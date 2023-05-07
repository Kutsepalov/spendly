package com.acceleron.spendly.core.service.impl;

import com.acceleron.spendly.core.dto.UserGroupDto;
import com.acceleron.spendly.core.dto.notification.NotificationDto;
import com.acceleron.spendly.core.dto.notification.data.GroupInvitation;
import com.acceleron.spendly.core.mapper.UserGroupMapper;
import com.acceleron.spendly.core.service.AuthenticationService;
import com.acceleron.spendly.core.service.NotificationService;
import com.acceleron.spendly.core.service.UserGroupService;
import com.acceleron.spendly.persistence.dao.UserGroupDao;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserGroupServiceImpl implements UserGroupService {

    private final UserGroupDao userGroupDao;
    private final UserGroupMapper userGroupMapper;
    private final NotificationService notificationService;
    private final AuthenticationService authenticationService;

    @Autowired
    public UserGroupServiceImpl(UserGroupDao userGroupDao, UserGroupMapper userGroupMapper,
                                @Lazy NotificationService notificationService, //TODO: Solve circular dependency with NotificationService
                                AuthenticationService authenticationService) {
        this.userGroupDao = userGroupDao;
        this.userGroupMapper = userGroupMapper;
        this.notificationService = notificationService;
        this.authenticationService = authenticationService;
    }

    @Override
    public UserGroupDto findById(UUID id) {
        return userGroupDao.findById(id)
                .map(userGroupMapper::toDto).orElse(null);
    }

    @Override
    public UserGroupDto save(UserGroupDto userGroupDto) {
        userGroupDto.getUserIds().add(authenticationService.getCurrentUserId());
        return userGroupMapper.toDto(
                userGroupDao.save(userGroupMapper.toEntity(userGroupDto)));
    }

    @Override
    public UserGroupDto update(UserGroupDto userGroupDto) {
        if (userGroupDto.getId() == null) {
            throw new IllegalIdentifierException("Group ID is required.");
        }
        return userGroupMapper.toDto(
                userGroupDao.save(userGroupMapper.toEntity(userGroupDto)));
    }

    @Override
    public UserGroupDto delete(UUID id) {
        UserGroupDto deletedUserGroupDto = findById(id);
        userGroupDao.delete(id, authenticationService.getCurrentUserId());
        return deletedUserGroupDto;
    }

    @Override
    public List<UserGroupDto> findAll() {
        return userGroupDao.findAll(authenticationService.getCurrentUserId())
                .stream()
                .map(userGroupMapper::toDto).toList();
    }

    @Override
    public void inviteUserIntoGroup(UUID userId, UUID groupId) {
        UserGroupDto userGroup = Optional.of(findById(groupId))
                .orElseThrow(() -> new NullPointerException("There is no group with id: " + groupId));

        NotificationDto notification = NotificationDto.builder()
                .senderId(authenticationService.getCurrentUserId())
                .receiverId(userId)
                .type(NotificationDto.NotificationType.INVITATION)
                .data(new GroupInvitation(userGroup))
                .build();

        notificationService.sendNotification(notification);
    }

    @Override
    public UserGroupDto enterGroup(UUID groupId) {
        UserGroupDto group = findById(groupId);
        group.getUserIds().add(authenticationService.getCurrentUserId());
        group = update(group);
        return group;
    }
}
