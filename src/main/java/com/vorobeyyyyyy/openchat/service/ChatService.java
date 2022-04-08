package com.vorobeyyyyyy.openchat.service;

import com.vorobeyyyyyy.openchat.model.domain.Chat;
import com.vorobeyyyyyy.openchat.model.domain.Message;
import com.vorobeyyyyyy.openchat.model.dto.request.MessageFilter;
import com.vorobeyyyyyy.openchat.model.dto.request.UpdateMessageDto;
import com.vorobeyyyyyy.openchat.model.dto.response.ChatDto;
import com.vorobeyyyyyy.openchat.model.dto.request.ChatFilter;
import com.vorobeyyyyyy.openchat.model.dto.request.UpdateChatDto;
import com.vorobeyyyyyy.openchat.model.dto.response.MessageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ChatService {
    Page<ChatDto> getChats(ChatFilter chatFilter, Pageable pageable);

    ChatDto getChat(UUID chatUuid);

    void createOrUpdateChat(UpdateChatDto chatDto);

    void deleteChat(UUID chatUuid);

    Page<MessageDto> getChatMessages(UUID chatUuid, MessageFilter messageFilter, Pageable pageable);

    void createOrUpdateMessage(UUID chatUuid, UpdateMessageDto messageDto);

    void deleteMessage(UUID chatUuid, UUID messageUuid);

    Chat getChatEntity(UUID chatUuid);

    Message getMessageEntity(UUID chatUuid, UUID messageUuid);

    Message getMessageEntity(UUID messageUuid);
}
