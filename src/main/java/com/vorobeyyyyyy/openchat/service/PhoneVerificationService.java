package com.vorobeyyyyyy.openchat.service;

import com.vorobeyyyyyy.openchat.model.dto.request.SendCodeRequest;

public interface PhoneVerificationService {
    void sendCode(SendCodeRequest sendCodeRequest);

}
