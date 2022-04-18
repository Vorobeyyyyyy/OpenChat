package com.vorobeyyyyyy.openchat.service;

import java.util.UUID;

public interface WebsocketService {
    void sendToUser(UUID userUuid, Object message);
}
