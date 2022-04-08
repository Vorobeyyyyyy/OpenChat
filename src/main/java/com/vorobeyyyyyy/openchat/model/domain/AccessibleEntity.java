package com.vorobeyyyyyy.openchat.model.domain;

public interface AccessibleEntity {
    boolean canBeModifiedBy(User user);

    boolean canBeDeletedBy(User user);
}
