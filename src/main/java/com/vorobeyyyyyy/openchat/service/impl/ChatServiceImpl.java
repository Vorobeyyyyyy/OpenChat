package com.vorobeyyyyyy.openchat.service.impl;

import com.vorobeyyyyyy.openchat.model.converter.ChatDtoConverter;
import com.vorobeyyyyyy.openchat.model.dto.ChatDto;
import com.vorobeyyyyyy.openchat.model.dto.ChatFilter;
import com.vorobeyyyyyy.openchat.repository.ChatRepository;
import com.vorobeyyyyyy.openchat.service.ChatService;
import com.vorobeyyyyyy.openchat.util.AuthUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class ChatServiceImpl implements ChatService {

    private ChatRepository chatRepository;

    private ChatDtoConverter chatDtoConverter;

    @Override
    public Page<ChatDto> getChats(ChatFilter chatFilter, Pageable pageable) {
        UUID userUuid = AuthUtils.getUserUuid();
        return chatRepository.findAll(chatFilter, pageable)
                .map(chatDtoConverter::toDto);
    }
}
