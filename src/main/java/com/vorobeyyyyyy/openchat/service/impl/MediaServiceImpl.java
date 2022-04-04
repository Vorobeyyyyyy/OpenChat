package com.vorobeyyyyyy.openchat.service.impl;

import com.vorobeyyyyyy.openchat.model.domain.Media;
import com.vorobeyyyyyy.openchat.model.dto.response.MediaDto;
import com.vorobeyyyyyy.openchat.service.MediaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.UUID;

@Service
@AllArgsConstructor
public class MediaServiceImpl implements MediaService {
    @Override
    public MediaDto upload(MultipartFile media) {
        return null; // TODO
    }

    @Override
    public ResponseEntity<StreamingResponseBody> download(UUID mediaUuid) {
        return null; // TODO
    }

    @Override
    public ResponseEntity<StreamingResponseBody> downloadThumbnail(UUID mediaUuid) {
        return null; // TODO
    }

    @Override
    public Media findImage(UUID imageMediaUuid) {
        return null; // TODO
    }
}
