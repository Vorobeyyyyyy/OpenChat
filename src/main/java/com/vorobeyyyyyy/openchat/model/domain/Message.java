package com.vorobeyyyyyy.openchat.model.domain;

import com.vorobeyyyyyy.openchat.model.base.UuidEntity;
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
public class Message extends UuidEntity implements AccessibleEntity {

    @ManyToOne
    @JoinColumn(name = "sender_uuid")
    private User sender;

    private String text;

    @ManyToOne
    @JoinColumn(name = "chat_uuid")
    private Chat chat;

    @OneToMany(mappedBy = "message")
    private List<Media> attachments;

    @ManyToOne
    @JoinColumn(name = "redirected_message_uuid")
    private Message redirectedMessage;

    @ManyToOne
    @JoinColumn(name = "reply_to_message_uuid")
    private Message replyMessage;

    @Override
    public boolean canBeModifiedBy(User user) {
        return false; // TODO
    }

    @Override
    public boolean canBeDeletedBy(User user) {
        return false; // TODO
    }
}
