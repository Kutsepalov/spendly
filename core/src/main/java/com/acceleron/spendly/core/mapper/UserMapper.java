package com.acceleron.spendly.core.mapper;

import com.acceleron.spendly.core.dto.UserDataDto;
import com.acceleron.spendly.persistence.entity.User;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDataDto toUserDto(User model);
    User toUserEntity(UserDataDto model);
    User toUserEntity(UserDataDto model, UUID id);
}
