package com.vorobeyyyyyy.openchat.security;

import com.vorobeyyyyyy.openchat.exception.TokenNotPresentException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.HandshakeHandler;

import java.util.Map;
import java.util.Optional;

@Component
@AllArgsConstructor
public class WebsocketAuthInterceptor implements HandshakeHandler {

    @Override
    public boolean doHandshake(@NonNull ServerHttpRequest request,
                               @NonNull ServerHttpResponse response,
                               @NonNull WebSocketHandler wsHandler,
                               @NonNull Map<String, Object> attributes) throws HandshakeFailureException {
        String token = Optional.ofNullable(request.getHeaders().get(HttpHeaders.AUTHORIZATION))
                .map(headers -> headers.get(0))
                .orElseThrow(TokenNotPresentException::new);
        return true; // TODO
    }
}
