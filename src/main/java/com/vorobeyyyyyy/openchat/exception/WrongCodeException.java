package com.vorobeyyyyyy.openchat.exception;

import com.vorobeyyyyyy.openchat.constant.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WrongCodeException extends BaseException {

    public WrongCodeException() {
        this.code = ErrorCode.WRONG_CODE;
        this.message = "Wrong credentials";
        this.messageKey = "exceptions.wrongCredentials";
    }
}
