package com.vorobeyyyyyy.openchat.model.domain;

import com.vorobeyyyyyy.openchat.model.base.UuidEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "media")
public class Media extends UuidEntity {

    @OneToOne
    @JoinColumn(name = "chat_uuid")
    private Chat chat;

    // TODO
}