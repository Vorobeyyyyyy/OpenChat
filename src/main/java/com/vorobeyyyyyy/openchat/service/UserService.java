package com.vorobeyyyyyy.openchat.service;

import com.vorobeyyyyyy.openchat.model.dto.LoginRequestDto;
import com.vorobeyyyyyy.openchat.model.dto.LoginResponseDto;

public interface UserService {

	LoginResponseDto login(LoginRequestDto loginRequestDto);
}
