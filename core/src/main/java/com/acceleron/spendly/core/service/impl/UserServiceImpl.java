package com.acceleron.spendly.core.service.impl;

import com.acceleron.spendly.core.dto.UserDataDto;
import com.acceleron.spendly.core.mapper.UserMapper;
import com.acceleron.spendly.core.service.UserService;
import com.acceleron.spendly.persistence.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.acceleron.spendly.core.util.UserAuthenticationMatcher.isEmail;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserMapper userMapper;

    @Override
    public UserDataDto findById(UUID id) {
        return userDao.findById(id)
                .map(userMapper::toUserDto)
                .orElse(null);
    }

    @Override
    public Optional<UserDataDto> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userDao.findByUsername(authentication.getName()).map(userMapper::toUserDto);
    }

    @Override
    public Optional<UserDataDto> findByUsernameOrEmail(String usernameOrEmail) {
        if (isEmail(usernameOrEmail)) {
            return userDao.findByEmail(usernameOrEmail).map(userMapper::toUserDto);
        }
        return userDao.findByUsername(usernameOrEmail).map(userMapper::toUserDto);
    }

    @Override
    public Optional<UserDataDto> findByUsername(String username) {
        return userDao.findByUsername(username)
                .map(userMapper::toUserDto);
    }

    @Override
    public UserDataDto save(UserDataDto user) {
        return userMapper.toUserDto(
                userDao.save(userMapper.toUserEntity(user))
        );
    }

    @Override
    public UserDataDto update(UserDataDto user) {
        if (user.getId() == null) {
            throw new IllegalIdentifierException("Account ID is required.");
        }
        return userMapper.toUserDto(
                userDao.save(userMapper.toUserEntity(user))
        );
    }

    @Override
    public UserDataDto delete(UUID id) {
        return userMapper.toUserDto(userDao.deleteUserById(id));
    }
}
