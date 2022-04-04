package com.vorobeyyyyyy.openchat.service.impl;

import com.vorobeyyyyyy.openchat.model.converter.ChatDtoConverter;
import com.vorobeyyyyyy.openchat.model.domain.Chat;
import com.vorobeyyyyyy.openchat.model.domain.User;
import com.vorobeyyyyyy.openchat.model.dto.request.MessageFilter;
import com.vorobeyyyyyy.openchat.model.dto.request.UpdateMessageDto;
import com.vorobeyyyyyy.openchat.model.dto.response.ChatDto;
import com.vorobeyyyyyy.openchat.model.dto.request.ChatFilter;
import com.vorobeyyyyyy.openchat.model.dto.request.UpdateChatDto;
import com.vorobeyyyyyy.openchat.model.dto.response.MessageDto;
import com.vorobeyyyyyy.openchat.model.enumerated.ChatType;
import com.vorobeyyyyyy.openchat.repository.ChatRepository;
import com.vorobeyyyyyy.openchat.repository.UserRepository;
import com.vorobeyyyyyy.openchat.service.ChatService;
import com.vorobeyyyyyy.openchat.service.MediaService;
import com.vorobeyyyyyy.openchat.service.UserService;
import com.vorobeyyyyyy.openchat.util.ExceptionUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class ChatServiceImpl implements ChatService {

    private ChatRepository chatRepository;

    private ChatDtoConverter chatDtoConverter;

    private UserService userService;

    private UserRepository userRepository;

    private MediaService mediaService;

    @Override
    public Page<ChatDto> getChats(ChatFilter chatFilter, Pageable pageable) {
        return chatRepository.findAll(userService.getCurrentUser(), chatFilter, pageable)
                .map(chatDtoConverter::toDto);
    }

    @Override
    public ChatDto getChat(UUID chatUuid) {
        Chat chat = getChatEntity(chatUuid);
        return chatDtoConverter.toDto(chat);
    }

    @Override
    public void createOrUpdateChat(UpdateChatDto chatDto) {
        Chat chat = Optional.ofNullable(chatDto.getUuid())
                .map(this::getChatEntity)
                .orElseGet(Chat::new);
        if (!chat.canBeModifiedBy(userService.getCurrentUser())) {
            ExceptionUtils.throwForbiddenException(Chat.class, chat.getUuid());
        }
        if (chatDto.getType().equals(ChatType.PRIVATE)
                && chatRepository.privateChatExists(chatDto.getUsers().stream().map(userRepository::getById).toList())) {
            ExceptionUtils.throwBadRequestException("exceptions.privateChatExists");
        }

        List<User> users = chatDto.getUsers().stream().map(userService::getUserEntity).toList();

        if (chat.isNew()) {
            // TODO: check if users accepts chats from other users
        }

        chat.setType(chatDto.getType());
        chat.setName(chatDto.getName());
        chat.setDescription(chatDto.getDescription());
        chat.setUsers(users);
        chat.setConfirmed(false);
        chat.setImage(mediaService.findImage(chatDto.getImageMediaUuid()));

        chatRepository.save(chat);

        // TODO: notify users
    }

    @Override
    public void deleteChat(UUID chatUuid) {
        //TODO
    }

    @Override
    public Page<MessageDto> getChatMessages(UUID chatUuid, MessageFilter messageFilter, Pageable pageable) {
        return null; //TODO
    }

    @Override
    public void createOrUpdateMessage(UUID chatUuid, UpdateMessageDto messageDto) {
        //TODO
    }

    @Override
    public void deleteMessage(UUID chatUuid, UUID messageUuid) {
        //TODO
    }

    @Override
    public Chat getChatEntity(UUID chatUuid) {
        return chatRepository.findByUsersContainsAndUuid(userService.getCurrentUser(), chatUuid)
                .orElseThrow(ExceptionUtils.notFoundExceptionSupplier(Chat.class, chatUuid));
    }
}
