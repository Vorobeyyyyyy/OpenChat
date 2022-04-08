package com.vorobeyyyyyy.openchat.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class NotFoundException extends BaseException {

    public NotFoundException(String messageKey) {
        super(404000, messageKey);
    }

    public NotFoundException(Class<?> clazz, UUID id) {
        super(404001, "exception.entityWithUuidNotFound");
        addParameter("entityName", clazz.getSimpleName());
        addParameter("entityUuid", id);
    }
}
