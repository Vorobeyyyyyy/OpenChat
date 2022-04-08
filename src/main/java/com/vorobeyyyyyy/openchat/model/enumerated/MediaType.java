package com.vorobeyyyyyy.openchat.model.enumerated;

import org.apache.commons.lang3.StringUtils;

public enum MediaType {
    IMAGE,
    VIDEO,
    AUDIO,
    FILE;

    public static MediaType byContentType(String contentType) {
        if (StringUtils.startsWith(contentType, "image")) {
            return IMAGE;
        } else if (StringUtils.startsWith(contentType, "video")) {
            return VIDEO;
        } else if (StringUtils.startsWith(contentType, "audio")) {
            return AUDIO;
        } else {
            return FILE;
        }
    }

    public boolean haveThumbnail() {
        return this == IMAGE || this == VIDEO;
    }
}