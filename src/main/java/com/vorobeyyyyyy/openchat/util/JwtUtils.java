package com.vorobeyyyyyy.openchat.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.vorobeyyyyyy.openchat.config.OpenChatProperties;
import com.vorobeyyyyyy.openchat.model.domain.User;
import com.vorobeyyyyyy.openchat.model.dto.TokenDto;
import com.vorobeyyyyyy.openchat.model.enumerated.Role;
import com.vorobeyyyyyy.openchat.security.AuthInformation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;


@Component
public class JwtUtils {

    private final Algorithm algorithm;

    private final OpenChatProperties properties;

    public JwtUtils(OpenChatProperties properties) {
        this.properties = properties;
        algorithm = Algorithm.HMAC256(properties.getSecurity().getJwt().getSecret());
    }

    public TokenDto createToken(User user) {

        long exp = System.currentTimeMillis() + properties.getSecurity().getJwt().getExpiration().toMillis();

        String token = JWT.create()
                .withClaim("uuid", user.getUuid().toString())
                .withClaim("username", user.getUsername())
                .withArrayClaim("roles", user.getRoles().stream().map(Role::name).toArray(String[]::new))
                .withClaim("expires", exp)
                .sign(algorithm);
        return TokenDto.builder()
                .expires(exp)
                .duration(properties.getSecurity().getJwt().getExpiration().toMillis())
                .token(token)
                .build();
    }

    public AuthInformation parseToken(String token) {
        Map<String, Claim> claims = JWT.require(algorithm)
                .build()
                .verify(token)
                .getClaims();
        return AuthInformation.builder()
                .userUuid(UUID.fromString(claims.get("uuid").asString()))
                .username(claims.get("username").asString())
                .authorities(claims.get("roles").asList(Role.class))
                .expires(claims.get("expires").asLong())
                .build();
    }
}
