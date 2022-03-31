package com.vorobeyyyyyy.openchat.service.impl;

import com.vorobeyyyyyy.openchat.config.OpenChatProperties;
import com.vorobeyyyyyy.openchat.exception.TooFrequentSendCodeException;
import com.vorobeyyyyyy.openchat.model.dto.SendCodeRequest;
import com.vorobeyyyyyy.openchat.repository.RedisRepository;
import com.vorobeyyyyyy.openchat.service.PhoneVerificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class PhoneVerificationServiceImpl implements PhoneVerificationService {

    private RedisRepository redisRepository;

    private OpenChatProperties properties;

    @Override
    public void sendCode(SendCodeRequest sendCodeRequest) {
        String code = RandomStringUtils.random(6, false, true);
        redisRepository.isWasSend(sendCodeRequest.getPhone()).ifPresent(timestamp -> {
            if (System.currentTimeMillis() - timestamp < properties.getSecurity().getResendCodeTimeout().toMillis()) {
                throw new TooFrequentSendCodeException();
            }
        });

        redisRepository.saveVerificationCode(sendCodeRequest.getPhone(), code);
        log.info("Code {} was sent to {}", code, sendCodeRequest.getPhone());
    }
}
