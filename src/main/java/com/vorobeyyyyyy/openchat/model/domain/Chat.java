package com.vorobeyyyyyy.openchat.model.domain;

import com.vorobeyyyyyy.openchat.model.base.UuidEntity;
import com.vorobeyyyyyy.openchat.model.enumerated.ChatType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

import static com.vorobeyyyyyy.openchat.util.ExceptionUtils.illegalArgumentExceptionIf;

@Entity
@Table(name = "chats")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Chat extends UuidEntity implements AccessableEntity {

    private String name;

    @Enumerated
    @Column(nullable = false, updatable = false)
    private ChatType type;

    private String description;

    @ManyToMany
    @JoinTable(
            name = "chat_users",
            joinColumns = @JoinColumn(name = "chat_uuid"),
            inverseJoinColumns = @JoinColumn(name = "user_uuid")
    )
    private List<User> users;

    @ManyToMany
    @JoinTable(
            name = "chat_moderators",
            joinColumns = @JoinColumn(name = "chat_uuid"),
            inverseJoinColumns = @JoinColumn(name = "user_uuid")
    )
    private List<User> moderators;

    @ManyToOne
    @JoinColumn(name = "owner_uuid")
    private User owner;

    @Column(nullable = false)
    private boolean confirmed = false;

    @OneToOne(mappedBy = "chat")
    private Media image;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.REMOVE)
    private List<Message> messages;

    @Column(name = "is_public", columnDefinition = "boolean default false", nullable = false)
    private boolean isPublic = false;

    @Override
    protected void prePersist() {
        super.prePersist();
        switch (type) {
            case PRIVATE -> {
                illegalArgumentExceptionIf(users.size() != 2, "Private chat must have exactly 2 users");
                illegalArgumentExceptionIf(owner != null, "Owner must be null for private chat");
                illegalArgumentExceptionIf(CollectionUtils.isEmpty(moderators), "Moderators must not be empty for private chat");
            }
            case CHANNEL, MULTI_USER -> {
                illegalArgumentExceptionIf(users.size() <= 0, "Channel or Multi-User chat must have at least 1 users");
                illegalArgumentExceptionIf(owner == null, "Channel or Multi-User chat must have owner");
            }
        }
    }

    @Override
    public boolean canBeModifiedBy(User user) {
        return uuid == null || owner.equals(user) || moderators.contains(user);
    }

    @Override
    public boolean canBeDeletedBy(User user) {
        return uuid == null
                || owner.equals(user)
                || (type == ChatType.PRIVATE && users.contains(user));
    }

    public boolean canSendMessages(User user) {
        return uuid == null
                || owner.equals(user)
                || (type == ChatType.PRIVATE && users.contains(user))
                || (type == ChatType.CHANNEL && moderators.contains(user));
    }
}
