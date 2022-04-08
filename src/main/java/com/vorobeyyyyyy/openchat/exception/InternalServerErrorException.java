package com.vorobeyyyyyy.openchat.exception;

public class InternalServerErrorException extends BaseException {
    public InternalServerErrorException(String message, String messageKey) {
        super(500000, message, messageKey);
    }
}
