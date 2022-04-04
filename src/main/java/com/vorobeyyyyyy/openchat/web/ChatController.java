package com.vorobeyyyyyy.openchat.web;

import com.vorobeyyyyyy.openchat.model.dto.request.MessageFilter;
import com.vorobeyyyyyy.openchat.model.dto.request.UpdateMessageDto;
import com.vorobeyyyyyy.openchat.model.dto.response.ChatDto;
import com.vorobeyyyyyy.openchat.model.dto.request.ChatFilter;
import com.vorobeyyyyyy.openchat.model.dto.response.MessageDto;
import com.vorobeyyyyyy.openchat.model.dto.request.UpdateChatDto;
import com.vorobeyyyyyy.openchat.service.ChatService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.UUID;

import static com.vorobeyyyyyy.openchat.constant.Route.Chat.*;

@RestController
@RequestMapping(
        value = CHAT,
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

    @GetMapping(value = CHAT_UUID)
    public ChatDto getChat(
            @PathVariable UUID chatUuid
    ) {
        return chatService.getChat(chatUuid);
    }

    @PostMapping
    public void createOrUpdateChat(
            @RequestBody @Valid UpdateChatDto chatDto
    ) {
        chatService.createOrUpdateChat(chatDto);
    }

    @DeleteMapping(value = CHAT_UUID)
    public void deleteChat(
            @PathVariable UUID chatUuid
    ) {
        chatService.deleteChat(chatUuid);
    }

    @GetMapping(value = CHAT_UUID + MESSAGES)
    public Page<MessageDto> getChatMessages(
            @PathVariable UUID chatUuid,
            MessageFilter messageFilter,
            Pageable pageable
    ) {
        return chatService.getChatMessages(chatUuid, messageFilter, pageable);
    }

    @PostMapping(value = CHAT_UUID + MESSAGES)
    public void createMessage(
            @PathVariable UUID chatUuid,
            @RequestBody @Valid UpdateMessageDto messageDto
    ) {
        chatService.createOrUpdateMessage(chatUuid, messageDto);
    }

    @DeleteMapping(value = CHAT_UUID + MESSAGES + MESSAGE_UUID)
    public void deleteMessage(
            @PathVariable UUID chatUuid,
            @PathVariable UUID messageUuid
    ) {
        chatService.deleteMessage(chatUuid, messageUuid);
    }
}
