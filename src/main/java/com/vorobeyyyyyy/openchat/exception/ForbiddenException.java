package com.vorobeyyyyyy.openchat.exception;

import java.util.UUID;

public class ForbiddenException extends BaseException {

    public ForbiddenException() {
        this.code = 403001;
        this.messageKey = "exception.notAllowed";
    }

    public static ForbiddenException forEntity(Class<?> entityClass, UUID uuid) {
        ForbiddenException exception = new ForbiddenException();
        exception.setMessage("Can not interact with entity " + entityClass.getSimpleName() + " with uuid " + uuid);
        exception.addParameter("entity", entityClass.getSimpleName());
        exception.addParameter("uuid", uuid);
        return exception;
    }
}
