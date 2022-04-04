package com.vorobeyyyyyy.openchat.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMessageDto {

    private UUID uuid;

    private String text;

    private UUID redirectedMessageUuid;

    private UUID replyMessageUuid;

    private List<UUID> attachmentUuids;
}
