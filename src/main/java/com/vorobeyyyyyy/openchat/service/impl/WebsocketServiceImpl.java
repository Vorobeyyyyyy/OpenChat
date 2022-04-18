package com.vorobeyyyyyy.openchat.service.impl;

import com.vorobeyyyyyy.openchat.constant.Route;
import com.vorobeyyyyyy.openchat.service.WebsocketService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class WebsocketServiceImpl implements WebsocketService {

    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendToUser(UUID userUuid, Object message) {
        messagingTemplate.convertAndSend(Route.WebSocket.USER + '/' + userUuid, message);
    }
}
