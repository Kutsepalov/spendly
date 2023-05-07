package com.acceleron.spendly.core.service;

import com.acceleron.spendly.core.dto.UserGroupDto;

import java.util.List;
import java.util.UUID;

public interface UserGroupService extends CrudService<UserGroupDto, UUID> {

    List<UserGroupDto> findAll();
    void inviteUserIntoGroup(UUID userId, UUID groupId);

    UserGroupDto enterGroup(UUID groupId);
}
