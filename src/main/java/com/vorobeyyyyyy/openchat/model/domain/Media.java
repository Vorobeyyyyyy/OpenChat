package com.vorobeyyyyyy.openchat.model.domain;

import com.vorobeyyyyyy.openchat.model.base.UuidEntity;
import com.vorobeyyyyyy.openchat.model.enumerated.MediaType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "media")
@EntityListeners(AuditingEntityListener.class)
public class Media extends UuidEntity {

    @OneToOne
    @JoinColumn(name = "uploaded_by_uuid", nullable = false, updatable = false)
    @CreatedBy
    private User uploadedBy;

    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    @Setter(AccessLevel.NONE)
    @Column(name = "is_used", columnDefinition = "boolean default false", nullable = false)
    private boolean used;

    private String name;

    @Column(name = "size", columnDefinition = "bigint default 0", nullable = false)
    private long size;

    @OneToOne
    @JoinColumn(name = "chat_uuid")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "message_uuid")
    private Message message;

    @OneToOne
    @JoinColumn(name = "avatar_for_user_uuid")
    private User user;

    @Column(nullable = false)
    private String mediaTypeRaw;

    @Override
    protected void preUpdate() {
        super.preUpdate();
        used = isUsed();
    }

    boolean isUsed() {
        return chat != null || message != null;
    }

    public boolean haveThumbnail() {
        return mediaType.haveThumbnail();
    }
}