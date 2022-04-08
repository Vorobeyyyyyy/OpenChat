package com.vorobeyyyyyy.openchat.model.converter;

import com.vorobeyyyyyy.openchat.model.domain.Message;
import com.vorobeyyyyyy.openchat.model.dto.response.MessageDto;
import org.springframework.stereotype.Component;

@Component
public class MessageDtoConverter extends BaseConverter<MessageDto, Message> {
    @Override
    public MessageDto toDto(Message message) {
        return null; // TODO
    }
}
