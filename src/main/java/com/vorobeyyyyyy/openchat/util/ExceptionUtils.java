package com.vorobeyyyyyy.openchat.util;

import com.vorobeyyyyyy.openchat.exception.BadRequestException;
import com.vorobeyyyyyy.openchat.exception.ForbiddenException;
import com.vorobeyyyyyy.openchat.exception.InternalServerErrorException;
import com.vorobeyyyyyy.openchat.exception.NotFoundException;

import java.io.IOException;
import java.util.UUID;
import java.util.function.Supplier;

public class ExceptionUtils {

    public static Supplier<NotFoundException> notFoundExceptionSupplier(Class<?> clazz, UUID uuid) {
        return () -> new NotFoundException(clazz, uuid);
    }

    public static void illegalArgumentExceptionIf(boolean option, String message) {
        if (option) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void badRequestExceptionIf(boolean option, String messageKey) {
        if (option) {
            throw new BadRequestException(messageKey);
        }
    }

    public static void throwForbiddenException(Class<?> chatClass, UUID uuid) {
        throw ForbiddenException.forEntity(chatClass, uuid);
    }

    public static void throwBadRequestException(String messageKey) {
        throw new BadRequestException(messageKey);
    }

    public static InternalServerErrorException internalServerErrorException(String message, String messageKey) {
        return new InternalServerErrorException(message, messageKey);
    }
}
