package com.vorobeyyyyyy.openchat.model.converter;

import com.vorobeyyyyyy.openchat.constant.Route;
import com.vorobeyyyyyy.openchat.model.domain.Media;
import com.vorobeyyyyyy.openchat.model.dto.response.MediaDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MediaDtoConverter extends BaseConverter<MediaDto, Media> {

    @Override
    public MediaDto toDto(Media media) {
        return MediaDto.builder()
                .uuid(media.getUuid())
                .name(media.getName())
                .size(media.getSize())
                .type(media.getMediaType())
                .url(buildUrl(media.getUuid()))
                .thumbnailUrl(buildThumbnailUrl(media))
                .build();
    }

    private String buildUrl(UUID uuid) {
        return Route.Media.MEDIA + "/" + uuid;
    }

    private String buildThumbnailUrl(Media media) {
        if (media.getMediaType().haveThumbnail()) {
            return buildUrl(media.getUuid()) + Route.Media.THUMBNAIL;
        } else {
            return null;
        }
    }
}
