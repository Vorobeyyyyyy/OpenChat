package com.vorobeyyyyyy.openchat.model.converter;

import com.vorobeyyyyyy.openchat.model.domain.Chat;
import com.vorobeyyyyyy.openchat.model.domain.User;
import com.vorobeyyyyyy.openchat.model.dto.ChatDto;
import com.vorobeyyyyyy.openchat.model.enumerated.ChatType;
import com.vorobeyyyyyy.openchat.util.AuthUtils;
import org.springframework.stereotype.Component;

@Component
public class ChatDtoConverter extends BaseConverter<ChatDto, Chat> {

    @Override
    public ChatDto toDto(Chat chat) {
        return ChatDto.builder()
                .uuid(chat.getUuid())
                .name(buildName(chat))
                .type(chat.getType())
                .confirmed(buildConfirmed(chat))
                .build();
    }

    private boolean buildConfirmed(Chat chat) {
        if (chat.getType().equals(ChatType.PRIVATE)) {
            return findOppositeUser(chat).isConfirmed();
        } else {
            return chat.isConfirmed();
        }
    }

    private String buildName(Chat chat) {
        if (chat.getType().equals(ChatType.PRIVATE)) {
            return findOppositeUser(chat).getUsername();
        } else {
            return chat.getName();
        }
    }

    private User findOppositeUser(Chat chat) {
        return chat.getUsers().stream()
                .filter(user -> !user.getUuid().equals(AuthUtils.getUserUuid()))
                .findAny().orElseThrow(() -> new IllegalStateException("Private chat with not 2 members"));
    }
}
