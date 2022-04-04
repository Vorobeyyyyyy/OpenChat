package com.vorobeyyyyyy.openchat.exception;

public class BadRequestException extends BaseException {

    public BadRequestException(String messageKey) {
        this.code = 400000;
        this.messageKey = messageKey;
    }
}
