package com.vorobeyyyyyy.openchat.repository.impl;

import com.vorobeyyyyyy.openchat.repository.RedisRepository;
import com.vorobeyyyyyy.openchat.repository.projection.VerificationCodeProjection;
import com.vorobeyyyyyy.openchat.util.MapperUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Base64;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RedisRepositoryImpl implements RedisRepository {

    public final static String VERIFICATION_CODE_KEY = "verification_code:";

    @Autowired
    public RedisTemplate<String, String> redisTemplate;

    private MapperUtils mapperUtil;

    @Override
    public void saveVerificationCode(String phone, String code) {
        String key = buildVerificationCodeKey(phone);
        VerificationCodeProjection projection = VerificationCodeProjection.builder()
                .code(code)
                .created(System.currentTimeMillis())
                .build();
        redisTemplate.opsForValue().set(key, mapperUtil.writeAsString(projection), Duration.ofMinutes(5));
    }

    @Override
    public Optional<Long> isWasSend(String phone) {
        String projectionString = redisTemplate.opsForValue().get(buildVerificationCodeKey(phone));
        return Optional.ofNullable(projectionString)
                .map(s -> mapperUtil.readAsObject(s, VerificationCodeProjection.class).getCreated());
    }


    @Override
    public boolean checkVerificationCode(String phone, String code) {
        String projectionString = redisTemplate.opsForValue().get(buildVerificationCodeKey(phone));
        return Optional.ofNullable(projectionString)
                .map(s -> mapperUtil.readAsObject(s, VerificationCodeProjection.class).getCode().equals(code))
                .orElse(false);
    }

    private String buildVerificationCodeKey(String phone) {
        return VERIFICATION_CODE_KEY + Base64.getEncoder().encodeToString(phone.getBytes());
    }
}
