package com.vorobeyyyyyy.openchat.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@EnableConfigurationProperties
@ConfigurationProperties(prefix = "openchat")
@Component
@Data
public class OpenChatProperties {

	private Security security;

	@Data
	public static class Security {

		private String secret;
	}
}