package com.vorobeyyyyyy.openchat.model.dto.response;

import com.vorobeyyyyyy.openchat.model.enumerated.ChatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatDto {

    private UUID uuid;

    private String name;

    private MessageDto lastMessage;

    private ChatType type;

    private boolean notificationsEnabled;

    private long unreadMessages;

    private String avatarUrl;

    private boolean confirmed;
}
