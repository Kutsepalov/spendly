package com.acceleron.spendly.core.mapper;

import com.acceleron.spendly.core.dto.UserDto;
import com.acceleron.spendly.persistence.entity.User;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User model);
    User toUserEntity(UserDto model);
    User toUserEntity(UserDto model, UUID id);
}
