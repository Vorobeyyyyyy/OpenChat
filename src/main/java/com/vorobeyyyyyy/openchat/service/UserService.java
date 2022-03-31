package com.vorobeyyyyyy.openchat.service;

import com.vorobeyyyyyy.openchat.model.domain.User;
import com.vorobeyyyyyy.openchat.model.dto.LoginRequestDto;
import com.vorobeyyyyyy.openchat.model.dto.TokenDto;
import com.vorobeyyyyyy.openchat.model.dto.UserDto;

public interface UserService {

	TokenDto login(LoginRequestDto loginRequestDto);

    UserDto getMyself();

    User getAuthenticatedUser();
}
