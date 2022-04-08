package com.vorobeyyyyyy.openchat.repository;

import com.querydsl.core.BooleanBuilder;
import com.vorobeyyyyyy.openchat.model.domain.Message;
import com.vorobeyyyyyy.openchat.model.domain.QChat;
import com.vorobeyyyyyy.openchat.model.domain.QMessage;
import com.vorobeyyyyyy.openchat.model.dto.request.MessageFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID>, BaseCustomRepository {

    QChat chat = QChat.chat;
    QMessage message = QMessage.message;

    Optional<Message> findByChatUuidAndUuid(UUID chatUuid, UUID uuid);

    default Optional<Message> findByUuidAndUserUuid(UUID messageUuid, UUID userUuid) {
        return Optional.ofNullable(selectFrom(message).where(message.uuid.eq(messageUuid),
                message.chat.users.any().uuid.eq(userUuid)).fetchOne());
    }

    default Page<Message> findAll(UUID chatUuid, MessageFilter messageFilter, Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (StringUtils.isNotEmpty(messageFilter.getText())) {
            booleanBuilder.and(message.text.containsIgnoreCase(messageFilter.getText()));
        }
        if (messageFilter.getSenderUuid() != null) {
            booleanBuilder.and(message.sender.uuid.eq(messageFilter.getSenderUuid()));
        }
        return toPage(selectFrom(message, pageable).where(message.chat.uuid.eq(chatUuid)));
    }
}
