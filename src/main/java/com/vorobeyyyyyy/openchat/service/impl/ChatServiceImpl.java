package com.vorobeyyyyyy.openchat.service.impl;

import com.vorobeyyyyyy.openchat.model.converter.ChatDtoConverter;
import com.vorobeyyyyyy.openchat.model.converter.MessageDtoConverter;
import com.vorobeyyyyyy.openchat.model.domain.Chat;
import com.vorobeyyyyyy.openchat.model.domain.Media;
import com.vorobeyyyyyy.openchat.model.domain.Message;
import com.vorobeyyyyyy.openchat.model.domain.User;
import com.vorobeyyyyyy.openchat.model.dto.request.MessageFilter;
import com.vorobeyyyyyy.openchat.model.dto.request.UpdateMessageDto;
import com.vorobeyyyyyy.openchat.model.dto.response.ChatDto;
import com.vorobeyyyyyy.openchat.model.dto.request.ChatFilter;
import com.vorobeyyyyyy.openchat.model.dto.request.UpdateChatDto;
import com.vorobeyyyyyy.openchat.model.dto.response.MessageDto;
import com.vorobeyyyyyy.openchat.model.enumerated.ChatType;
import com.vorobeyyyyyy.openchat.repository.ChatRepository;
import com.vorobeyyyyyy.openchat.repository.MessageRepository;
import com.vorobeyyyyyy.openchat.repository.UserRepository;
import com.vorobeyyyyyy.openchat.service.ChatService;
import com.vorobeyyyyyy.openchat.service.MediaService;
import com.vorobeyyyyyy.openchat.service.UserService;
import com.vorobeyyyyyy.openchat.util.AuthUtils;
import com.vorobeyyyyyy.openchat.util.ExceptionUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ChatServiceImpl implements ChatService {

    private ChatRepository chatRepository;

    private ChatDtoConverter chatDtoConverter;

    private UserService userService;

    private UserRepository userRepository;

    private MediaService mediaService;

    private MessageRepository messageRepository;

    private MessageDtoConverter messageDtoConverter;

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
        Chat chat = getChatEntity(chatUuid);
        if (!chat.canBeDeletedBy(userService.getCurrentUser())) {
            ExceptionUtils.throwForbiddenException(Chat.class, chat.getUuid());
        }
        chatRepository.delete(chat);
    }

    @Override
    public Page<MessageDto> getChatMessages(UUID chatUuid, MessageFilter messageFilter, Pageable pageable) {
        Chat chat = getChatEntity(chatUuid);
        return messageRepository.findAll(chat.getUuid(), messageFilter, pageable)
                .map(messageDtoConverter::toDto);
    }

    @Override
    public void createOrUpdateMessage(UUID chatUuid, UpdateMessageDto messageDto) {
        Chat chat = getChatEntity(chatUuid);
        if (chat.canSendMessages(userService.getCurrentUser())) {
            ExceptionUtils.throwForbiddenException(Chat.class, chat.getUuid());
        }
        Message message = Optional.ofNullable(messageDto.getUuid())
                .map(messageUuid -> getMessageEntity(chatUuid, messageUuid))
                .orElseGet(Message::new);

        if (message.canBeModifiedBy(userService.getCurrentUser())) {
            ExceptionUtils.throwForbiddenException(Message.class, message.getUuid());
        }

        message.setText(messageDto.getText());

        if (message.isNew()) {
            Optional.ofNullable(messageDto.getRedirectedMessageUuid())
                    .map(this::getMessageEntity)
                    .ifPresent(message::setRedirectedMessage);
            Optional.ofNullable(messageDto.getReplyMessageUuid())
                    .map(uuid -> getMessageEntity(chatUuid, uuid))
                    .ifPresent(message::setReplyMessage);
        }

        Optional.ofNullable(messageDto.getAttachmentUuids())
                .ifPresent(uuids -> message.setAttachments(uuids.stream().map(mediaService::findImage).toList()));

        messageRepository.save(message);
    }

    @Override
    public void deleteMessage(UUID chatUuid, UUID messageUuid) {
        Chat chat = getChatEntity(chatUuid);
        if (!chat.canSendMessages(userService.getCurrentUser())) {
            ExceptionUtils.throwForbiddenException(Chat.class, chat.getUuid());
        }

        Message message = getMessageEntity(chatUuid, messageUuid);
        if (!message.canBeDeletedBy(userService.getCurrentUser())) {
            ExceptionUtils.throwForbiddenException(Message.class, message.getUuid());
        }
        messageRepository.delete(message);
    }

    @Override
    public Chat getChatEntity(UUID chatUuid) {
        return chatRepository.findByUsersContainsAndUuid(userService.getCurrentUser(), chatUuid)
                .orElseThrow(ExceptionUtils.notFoundExceptionSupplier(Chat.class, chatUuid));
    }

    @Override
    public Message getMessageEntity(UUID chatUuid, UUID messageUuid) {
        return messageRepository.findByChatUuidAndUuid(chatUuid, messageUuid)
                .orElseThrow(ExceptionUtils.notFoundExceptionSupplier(Message.class, messageUuid));
    }

    @Override
    public Message getMessageEntity(UUID messageUuid) {
        return messageRepository.findByUuidAndUserUuid(messageUuid, AuthUtils.getUserUuid())
                .orElseThrow(ExceptionUtils.notFoundExceptionSupplier(Message.class, messageUuid));
    }
}
