package com.vorobeyyyyyy.openchat.repository;

import java.util.Optional;

public interface RedisRepository {

    void saveVerificationCode(String phone, String code);

    Optional<Long> isWasSend(String phone);

    boolean checkVerificationCode(String phone, String code);


}
