package com.vorobeyyyyyy.openchat.model.domain;

import com.vorobeyyyyyy.openchat.model.base.UuidEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class RefreshToken extends UuidEntity {

    @Column(nullable = false, updatable = false)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_uuid", nullable = false, updatable = false)
    private User user;

    private boolean isRevoked;

    private String ip;

    private String device;

    private String location;
}
