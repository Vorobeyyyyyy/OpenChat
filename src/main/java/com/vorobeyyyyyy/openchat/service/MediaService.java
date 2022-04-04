package com.vorobeyyyyyy.openchat.service;

import com.vorobeyyyyyy.openchat.model.domain.Media;
import com.vorobeyyyyyy.openchat.model.dto.response.MediaDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.UUID;

public interface MediaService {

    MediaDto upload(MultipartFile media);

    ResponseEntity<StreamingResponseBody> download(UUID mediaUuid);

    ResponseEntity<StreamingResponseBody> downloadThumbnail(UUID mediaUuid);

    Media findImage(UUID imageMediaUuid);
}
