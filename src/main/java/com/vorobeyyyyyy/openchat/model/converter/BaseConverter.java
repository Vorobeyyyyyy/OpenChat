package com.vorobeyyyyyy.openchat.model.converter;

public abstract class BaseConverter <DTO, DOMAIN> {

    public abstract DTO toDto(DOMAIN domain);
}
