package com.vorobeyyyyyy.openchat.service.impl;

import com.vorobeyyyyyy.openchat.config.OpenChatProperties;
import com.vorobeyyyyyy.openchat.model.converter.MediaDtoConverter;
import com.vorobeyyyyyy.openchat.model.domain.Media;
import com.vorobeyyyyyy.openchat.model.dto.response.MediaDto;
import com.vorobeyyyyyy.openchat.model.enumerated.MediaType;
import com.vorobeyyyyyy.openchat.repository.MediaRepository;
import com.vorobeyyyyyy.openchat.service.MediaService;
import com.vorobeyyyyyy.openchat.service.UserService;
import com.vorobeyyyyyy.openchat.util.ExceptionUtils;
import liquibase.util.file.FilenameUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class MediaServiceImpl implements MediaService {

    private S3Client s3Client;

    private MediaRepository mediaRepository;

    private OpenChatProperties openChatProperties;

    private MediaDtoConverter mediaDtoConverter;

    private UserService userService;

    private static final String THUMBNAIL_PREFIX = "thumbnail_";

    private static final String THUMBNAIL_MEDIA_TYPE = "image/jpeg";

    @Override
    @Transactional
    public MediaDto upload(MultipartFile file) {
        ExceptionUtils.badRequestExceptionIf(file.getContentType() == null, "exceptions.contentTypeNull");

        Media media = new Media();
        media.setName(FilenameUtils.getName(file.getOriginalFilename()));
        media.setMediaType(MediaType.byContentType(file.getContentType()));
        media.setSize(file.getSize());
        media.setMediaTypeRaw(file.getContentType());
        Media savedMedia = mediaRepository.save(media);

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(getBucketName())
                        .key(savedMedia.getUuid().toString())
                        .build(),
                RequestBody.fromInputStream(safelyGetInputStream(file), file.getSize())
        );
        if (media.getMediaType().haveThumbnail()) {
            createThumbnailAndSaveForMedia(media, file);
        }
        return mediaDtoConverter.toDto(savedMedia);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<StreamingResponseBody> download(UUID mediaUuid) {
        Media media = findMediaInternal(mediaUuid);
        return downloadFile(mediaUuid.toString(), media.getMediaTypeRaw());
    }

    @Override
    public Media findMediaInternal(UUID mediaUuid) {
        return mediaRepository.findByUuid(mediaUuid, userService.getCurrentUser())
                .orElseThrow(ExceptionUtils.notFoundExceptionSupplier(Media.class, mediaUuid));
    }

    @Override
    public ResponseEntity<StreamingResponseBody> downloadThumbnail(UUID mediaUuid) {
        Media media = findMediaInternal(mediaUuid);
        ExceptionUtils.badRequestExceptionIf(!media.haveThumbnail(), "exceptions.thisMediaHaveNoThumbnail");
        return downloadFile(THUMBNAIL_PREFIX + mediaUuid.toString(), THUMBNAIL_MEDIA_TYPE);
    }

    @Override
    public Media findImage(UUID imageMediaUuid) {
        return mediaRepository.findByUuidAndMediaType(imageMediaUuid, userService.getCurrentUser(), MediaType.IMAGE)
                .orElseThrow(ExceptionUtils.notFoundExceptionSupplier(Media.class, imageMediaUuid));
    }

    @Override
    public Media findUnusedMedia(UUID attachmentMediaUuid) {
        return mediaRepository.findUnusedMedia(attachmentMediaUuid, userService.getCurrentUser())
                .orElseThrow(ExceptionUtils.notFoundExceptionSupplier(Media.class, attachmentMediaUuid));
    }

    private ResponseEntity<StreamingResponseBody> downloadFile(String fileKey, String mediaType) {
        ResponseInputStream<GetObjectResponse> object = s3Client.getObject(
                GetObjectRequest.builder()
                        .bucket(getBucketName())
                        .key(fileKey)
                        .build()
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(object.response().contentLength())
                .contentType(org.springframework.http.MediaType.valueOf(mediaType))
                .body(object::transferTo);
    }

    private InputStream safelyGetInputStream(InputStreamSource streamSource) {
        try {
            return streamSource.getInputStream();
        } catch (IOException e) {
            throw ExceptionUtils.internalServerErrorException(e.getMessage(), "exceptions.fileUploadError");
        }
    }

    private String getBucketName() {
        return openChatProperties.getStorage().getBucketName();
    }

    private void createThumbnailAndSaveForMedia(Media media, MultipartFile file) {
        // TODO
//        try {
//            file.getBytes();
//        } catch (Exception exception) {
//            log.error("Unable to create thumbnail for media {}", media.getUuid(), exception);
//        }
    }
}
