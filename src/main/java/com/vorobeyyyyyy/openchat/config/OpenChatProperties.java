package com.vorobeyyyyyy.openchat.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

import java.net.URI;
import java.time.Duration;

@EnableConfigurationProperties
@ConfigurationProperties(prefix = "openchat")
@Component
@Data
public class OpenChatProperties {

	private Security security;

	private Storage storage;

	@Data
	public static class Security {

		private Jwt jwt;

		private Duration resendCodeTimeout;

		@Data
		public static class Jwt {

			private String secret;

			private Duration expiration;
		}

	}

	@Data
	public static class Storage {

		private URI endpoint;

		private String accessKey;

		private String secretKey;

		private String bucketName;
	}
}
