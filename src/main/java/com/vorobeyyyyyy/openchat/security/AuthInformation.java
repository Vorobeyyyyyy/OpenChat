package com.vorobeyyyyyy.openchat.security;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthInformation {

	private String token;

	private long expiresIn;

	private String username;

	private UUID userUuid;

	private List<GrantedAuthority> authorities;
}
