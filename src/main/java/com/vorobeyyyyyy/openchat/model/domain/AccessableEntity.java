package com.vorobeyyyyyy.openchat.model.domain;

public interface AccessableEntity {
    boolean canBeModifiedBy(User user);

    boolean canBeDeletedBy(User user);
}
