package com.vorobeyyyyyy.openchat.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class NotFoundException extends BaseException {

    public NotFoundException(String messageKey) {
        this.code = 404000;
        this.messageKey = messageKey;
    }

    public NotFoundException(Class<?> clazz, UUID id) {
        this.code = 404001;
        this.messageKey = "exception.entityWithUuidNotFound";
        addParameter("entityName", clazz.getSimpleName());
        addParameter("entityUuid", id);
    }
}
