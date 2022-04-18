package com.vorobeyyyyyy.openchat.constant;

public interface Route {

    String UUID_REGEX = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";

    interface Chat {
        String CHAT = "/chats";
        String CHAT_UUID = "/{chatUuid:" + UUID_REGEX + "}";
        String MESSAGES = "/messages";
        String MESSAGE_UUID = "/{messageUuid:" + UUID_REGEX + "}";
    }

    interface Media {
        String MEDIA = "/media";
        String MEDIA_UUID = "/{mediaUuid:" + UUID_REGEX + "}";
        String THUMBNAIL = "/thumbnail";
    }

    interface WebSocket {
        String WEB_SOCKET = "/ws";
        String USER = "/user";
        String CHAT = "/chat";
    }
}
