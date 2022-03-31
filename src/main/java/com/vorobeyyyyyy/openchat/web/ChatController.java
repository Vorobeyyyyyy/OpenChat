package com.vorobeyyyyyy.openchat.web;

import com.vorobeyyyyyy.openchat.model.dto.ChatDto;
import com.vorobeyyyyyy.openchat.model.dto.ChatFilter;
import com.vorobeyyyyyy.openchat.service.ChatService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = "/chats",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
@CrossOrigin
public class ChatController {

    private ChatService chatService;

    @GetMapping
    public Page<ChatDto> getChats(
            ChatFilter chatFilter,
            Pageable pageable
    ) {
        return chatService.getChats(chatFilter, pageable);
    }
}
