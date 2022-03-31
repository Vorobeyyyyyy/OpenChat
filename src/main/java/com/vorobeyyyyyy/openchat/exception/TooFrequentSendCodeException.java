package com.vorobeyyyyyy.openchat.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TooFrequentSendCodeException extends BaseException {

    public TooFrequentSendCodeException() {
        this.code = 400001;
        this.message = "Too frequent send code";
        this.messageKey = "exceptions.too_frequent_send_code";
    }
}
