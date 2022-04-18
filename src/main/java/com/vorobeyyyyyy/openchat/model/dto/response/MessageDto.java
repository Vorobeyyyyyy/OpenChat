package com.vorobeyyyyyy.openchat.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {

    private UUID senderUuid;

    private String text;

    private UUID chatUuid;

    private List<MediaDto> attachments;

    private MessageDto redirectedMessage;

    private MessageDto replyToMessage;
}
