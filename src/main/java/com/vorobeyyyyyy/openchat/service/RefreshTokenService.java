package com.vorobeyyyyyy.openchat.service;

import com.vorobeyyyyyy.openchat.model.domain.User;

public interface RefreshTokenService {

    String createRefreshToken(User user);
}
