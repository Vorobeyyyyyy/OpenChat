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

    public void addParameter(String key, Object value) {
        if (additionalInfo == null) {
            additionalInfo = new HashMap<>();
        }
        additionalInfo.put(key, value);
    }
}
