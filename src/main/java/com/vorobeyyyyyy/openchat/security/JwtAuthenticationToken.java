package com.vorobeyyyyyy.openchat.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

	private final String accessToken;

	private final AuthInformation authInformation;

	public JwtAuthenticationToken() {
		super(null);
		this.setAuthenticated(false);
		this.accessToken = null;
		this.authInformation = null;
	}

	public JwtAuthenticationToken(String accessToken, AuthInformation authInformation) {
		super(null);
		this.setAuthenticated(true);
		this.accessToken = accessToken;
		this.authInformation = authInformation;
	}

	public static JwtAuthenticationToken unauthorized() {
		return new JwtAuthenticationToken();
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
