package com.vorobeyyyyyy.openchat.model.domain;

public interface AccessibleEntity {
    boolean canBeModifiedBy(User currentUser);
}
