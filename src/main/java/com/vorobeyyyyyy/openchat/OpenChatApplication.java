package com.vorobeyyyyyy.openchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OpenChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenChatApplication.class, args);
	}

}
