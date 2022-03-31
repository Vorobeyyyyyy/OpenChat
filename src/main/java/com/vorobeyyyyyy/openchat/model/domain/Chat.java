package com.vorobeyyyyyy.openchat.model.domain;

import com.vorobeyyyyyy.openchat.model.base.UuidEntity;
import com.vorobeyyyyyy.openchat.model.enumerated.ChatType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "chats")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Chat extends UuidEntity {

    private String name;

    @Enumerated
    @Column(nullable = false, updatable = false)
    private ChatType type;

    @ManyToMany
    @JoinTable(name = "chat_users")
    private List<User> users;

    @Column(nullable = false)
    private boolean confirmed = false;

    @Override
    protected void prePersist() {
        super.prePersist();
        if (type.equals(ChatType.PRIVATE)) {
            if (users.size() != 2) {
                throw new IllegalArgumentException("Private chat must have 2 users");
            }
        }
    }

}
