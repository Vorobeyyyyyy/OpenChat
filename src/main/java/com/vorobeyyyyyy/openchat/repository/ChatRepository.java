package com.vorobeyyyyyy.openchat.repository;

import com.querydsl.core.BooleanBuilder;
import com.vorobeyyyyyy.openchat.model.domain.Chat;
import com.vorobeyyyyyy.openchat.model.domain.QChat;
import com.vorobeyyyyyy.openchat.model.domain.QUser;
import com.vorobeyyyyyy.openchat.model.domain.User;
import com.vorobeyyyyyy.openchat.model.dto.request.ChatFilter;
import com.vorobeyyyyyy.openchat.model.enumerated.ChatType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID>, BaseCustomRepository {

    QChat chat = QChat.chat;
    QUser user = QUser.user;

    Optional<Chat> findByUsersContainsAndUuid(User user, UUID chatUuid);

    default Page<Chat> findAll(User user, ChatFilter filter, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.isNotEmpty(filter.getName())) {
            builder.and(chat.name.containsIgnoreCase(filter.getName()));
        }

        return fetchPageWhere(chat, pageable, chat.users.contains(user), builder);
    }

    default boolean privateChatExists(List<User> users) {
        return fetchOneWhere(chat,
                chat.type.eq(ChatType.PRIVATE),
                chat.users.contains(users.get(0)),
                chat.users.contains(users.get(1)),
                chat.users.size().eq(2)
        ).isPresent();
    }
}