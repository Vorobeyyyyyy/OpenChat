package com.vorobeyyyyyy.openchat.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseException extends RuntimeException{

    protected int code;

    protected String message;

    protected String messageKey;
}
