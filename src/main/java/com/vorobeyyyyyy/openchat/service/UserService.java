package com.vorobeyyyyyy.openchat.service;

import com.vorobeyyyyyy.openchat.model.domain.User;
import com.vorobeyyyyyy.openchat.model.dto.request.LoginRequestDto;
import com.vorobeyyyyyy.openchat.model.dto.response.TokenDto;
import com.vorobeyyyyyy.openchat.model.dto.response.UserDto;

import java.util.UUID;

public interface UserService {

	TokenDto login(LoginRequestDto loginRequestDto);

    UserDto getMyself();

    User getAuthenticatedUser();

    User getCurrentUser();

    User getUserEntity(UUID uuid);
}
