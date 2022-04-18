package com.vorobeyyyyyy.openchat.exception;

public class TokenNotPresentException extends BaseException {
    public TokenNotPresentException() {
        super(401000, "exceptions.tokenNotPresent");
    }
}
