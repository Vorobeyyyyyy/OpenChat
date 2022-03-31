package com.vorobeyyyyyy.openchat.service;

import com.vorobeyyyyyy.openchat.model.dto.SendCodeRequest;

public interface PhoneVerificationService {
    void sendCode(SendCodeRequest sendCodeRequest);

}
