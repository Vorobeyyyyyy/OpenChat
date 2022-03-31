package com.vorobeyyyyyy.openchat.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.List;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

	private final String accessToken;

	private final AuthInformation authInformation;

	private JwtAuthenticationToken() {
		super(List.of());
		this.setAuthenticated(false);
		this.accessToken = null;
		this.authInformation = null;
	}

	private JwtAuthenticationToken(AuthInformation authInformation) {
		super(authInformation.getAuthorities());
		this.setAuthenticated(true);
		this.accessToken = authInformation.getToken();
		this.authInformation = authInformation;
	}

	public static JwtAuthenticationToken unauthorized() {
		return new JwtAuthenticationToken();
	}

	public static JwtAuthenticationToken of(AuthInformation authInformation) {
		return new JwtAuthenticationToken(authInformation);
	}

	@Override
	public Object getCredentials() {
		return accessToken;
	}

	@Override
	public Object getPrincipal() {
		return authInformation;
	}
}
