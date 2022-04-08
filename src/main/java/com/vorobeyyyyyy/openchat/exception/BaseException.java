package com.vorobeyyyyyy.openchat.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseException extends RuntimeException{

    protected int code;

    protected String message;

    protected String messageKey;

    protected Map<String, Object> additionalInfo;

    public BaseException() {
    }

    public BaseException(int code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }

    public BaseException(int code, String message, String messageKey) {
        this.code = code;
        this.message = message;
        this.messageKey = messageKey;
    }

    public void addParameter(String key, Object value) {
        if (additionalInfo == null) {
            additionalInfo = new HashMap<>();
        }
        additionalInfo.put(key, value);
    }
}
