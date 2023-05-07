package com.acceleron.spendly.core.mapper;

import com.acceleron.spendly.core.dto.UserGroupDto;
import com.acceleron.spendly.persistence.dao.UserDao;
import com.acceleron.spendly.persistence.entity.User;
import com.acceleron.spendly.persistence.entity.UserGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class UserGroupMapper implements EntityMapper<UserGroup, UserGroupDto> {

    @Autowired
    private UserDao userDao;

    @Mapping(target = "userIds", expression = "java(mapToUUIDs(entity.getParticipants()))")
    public abstract UserGroupDto toDto(UserGroup entity);
    @Mapping(target = "participants", expression = "java(mapToUsers(dto.getUserIds()))")
    public abstract UserGroup toEntity(UserGroupDto dto);

    protected Set<User> mapToUsers(Set<UUID> participants) {
        return new HashSet<>(userDao.findAllById(participants));
    }

    protected Set<UUID> mapToUUIDs(Set<User> participants) {
        return participants.stream().map(User::getId).collect(Collectors.toSet());
    }
}
