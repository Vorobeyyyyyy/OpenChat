package com.vorobeyyyyyy.openchat.web;

import com.vorobeyyyyyy.openchat.model.dto.response.MediaDto;
import com.vorobeyyyyyy.openchat.service.MediaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.UUID;

import static com.vorobeyyyyyy.openchat.constant.Route.Media.*;

@RestController
@RequestMapping(value = MEDIA)
@AllArgsConstructor
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
@CrossOrigin
public class MediaController {

    private MediaService mediaService;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public MediaDto upload(
            MultipartFile media
    ) {
        return mediaService.upload(media);
    }

    @GetMapping(
            value = MEDIA_UUID
    )
    public ResponseEntity<StreamingResponseBody> download(
            @PathVariable UUID mediaUuid
    ) {
        return mediaService.download(mediaUuid);
    }

    @GetMapping(
            value = MEDIA_UUID + THUMBNAIL
    )
    public ResponseEntity<StreamingResponseBody> downloadThumbnail(
            @PathVariable UUID mediaUuid
    ) {
        return mediaService.downloadThumbnail(mediaUuid);
    }

}
