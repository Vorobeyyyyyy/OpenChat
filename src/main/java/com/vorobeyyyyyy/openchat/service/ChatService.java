package com.vorobeyyyyyy.openchat.service;

import com.vorobeyyyyyy.openchat.model.dto.ChatDto;
import com.vorobeyyyyyy.openchat.model.dto.ChatFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatService {
    Page<ChatDto> getChats(ChatFilter chatFilter, Pageable pageable);
}
