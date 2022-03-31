package com.vorobeyyyyyy.openchat.service.impl;

import com.vorobeyyyyyy.openchat.model.domain.RefreshToken;
import com.vorobeyyyyyy.openchat.model.domain.User;
import com.vorobeyyyyyy.openchat.repository.RefreshTokenRepository;
import com.vorobeyyyyyy.openchat.service.IpLocationService;
import com.vorobeyyyyyy.openchat.service.RefreshTokenService;
import com.vorobeyyyyyy.openchat.util.RequestUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private IpLocationService ipLocationService;

    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public String createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(RandomStringUtils.randomAlphanumeric(255));
        refreshToken.setDevice(RequestUtils.getUserAgent());
        refreshToken.setUser(user);
        refreshToken.setIp(RequestUtils.getIp());
        refreshToken.setRevoked(false);
        refreshToken.setLocation(ipLocationService.getLocation(RequestUtils.getIp()));
        refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }
}
