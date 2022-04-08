package com.vorobeyyyyyy.openchat.model.dto.response;

import com.vorobeyyyyyy.openchat.model.enumerated.MediaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MediaDto {

    private UUID uuid;

    private String name;

    private long size;

    private MediaType type;

    private String url;

    private String thumbnailUrl;
}
