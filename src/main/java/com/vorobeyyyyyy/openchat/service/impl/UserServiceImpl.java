package com.vorobeyyyyyy.openchat.service.impl;

import com.vorobeyyyyyy.openchat.exception.WrongCodeException;
import com.vorobeyyyyyy.openchat.model.domain.User;
import com.vorobeyyyyyy.openchat.model.dto.request.LoginRequestDto;
import com.vorobeyyyyyy.openchat.model.dto.response.TokenDto;
import com.vorobeyyyyyy.openchat.model.dto.response.UserDto;
import com.vorobeyyyyyy.openchat.model.enumerated.Role;
import com.vorobeyyyyyy.openchat.repository.RedisRepository;
import com.vorobeyyyyyy.openchat.repository.UserRepository;
import com.vorobeyyyyyy.openchat.service.RefreshTokenService;
import com.vorobeyyyyyy.openchat.service.UserService;
import com.vorobeyyyyyy.openchat.util.AuthUtils;
import com.vorobeyyyyyy.openchat.util.ExceptionUtils;
import com.vorobeyyyyyy.openchat.util.JwtUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private JwtUtils jwtUtil;

    private RedisRepository redisRepository;

    private RefreshTokenService refreshTokenService;

    @Override
    public TokenDto login(LoginRequestDto loginRequestDto) {
        boolean codeValid = redisRepository.checkVerificationCode(loginRequestDto.getPhone(), loginRequestDto.getCode());
        if (!codeValid) {
            throw new WrongCodeException();
        }
        boolean isNew = false;
        User user = userRepository.findByMobilePhone(loginRequestDto.getPhone())
            .orElseGet(User::new);
        if (user.getUuid() == null) {
            String newUsername = "User-" + RandomStringUtils.randomAlphanumeric(10);
            user.setUsername(newUsername);
            user.setMobilePhone(loginRequestDto.getPhone());
            user.setRoles(List.of(Role.USER));
            userRepository.save(user);
            isNew = true;
        }

        return jwtUtil.createToken(user)
                .withRefreshToken(refreshTokenService.createRefreshToken(user))
                .withNewAccount(isNew);
    }

    @Override
    public UserDto getMyself() {
        User user = getAuthenticatedUser();
        return UserDto.builder()
                .uuid(user.getUuid().toString())
                .username(user.getUsername())
                .build();
    }

    @Override
    public User getAuthenticatedUser() {
        return userRepository.findByUuid(AuthUtils.getUserUuid());
    }

    @Override
    public User getCurrentUser() {
        return userRepository.getById(AuthUtils.getUserUuid());
    }

    @Override
    public User getUserEntity(UUID uuid) {
        return userRepository.findById(uuid)
                .orElseThrow(ExceptionUtils.notFoundExceptionSupplier(User.class, uuid));
    }
}
