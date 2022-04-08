package com.vorobeyyyyyy.openchat.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.vorobeyyyyyy.openchat.model.domain.Media;
import com.vorobeyyyyyy.openchat.model.domain.QChat;
import com.vorobeyyyyyy.openchat.model.domain.QMedia;
import com.vorobeyyyyyy.openchat.model.domain.User;
import com.vorobeyyyyyy.openchat.model.enumerated.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MediaRepository extends JpaRepository<Media, UUID>, BaseCustomRepository {

    QMedia media = QMedia.media;

    default Optional<Media> findByUuid(UUID mediaUuid, User user) {
        return fetchOneWhere(media, commonFilters(mediaUuid, user));
    }

    default Optional<Media> findByUuidAndMediaType(UUID imageMediaUuid, User user, MediaType image) {
        return fetchOneWhere(media, commonFilters(imageMediaUuid, user), (media.mediaType.eq(image)));
    }


    default Optional<Media> findUnusedMedia(UUID attachmentMediaUuid, User currentUser) {
        return fetchOneWhere(media, commonFilters(attachmentMediaUuid, currentUser), media.used.isFalse());
    }

    private BooleanExpression commonFilters(UUID mediaUuid, User user) {
        return media.uuid.eq(mediaUuid).andAnyOf(
                availableAsUploader(user),
                availableAsAvatar(),
                availableAsChat(user),
                availableAsMessage(user));
    }

    private BooleanExpression availableAsUploader(User user) {
        return media.uploadedBy.eq(user);
    }

    private BooleanExpression availableAsAvatar() {
        return media.user.isNotNull();
    }

    private BooleanExpression availableAsChat(User user) {
        return availableAsChat(user, media.chat);
    }

    private BooleanExpression availableAsMessage(User user) {
        return availableAsChat(user, media.message.chat);
    }

    private BooleanExpression availableAsChat(User user, QChat qChat) {
        return qChat.users.contains(user)
                .or(qChat.isPublic.isTrue());
    }
}