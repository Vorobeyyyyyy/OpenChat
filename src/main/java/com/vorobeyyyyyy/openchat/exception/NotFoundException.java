package com.vorobeyyyyyy.openchat.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NotFoundException extends BaseException {
    public NotFoundException(String messageKey) {
        this.code = 404000;
        this.messageKey = messageKey;
    }
}
