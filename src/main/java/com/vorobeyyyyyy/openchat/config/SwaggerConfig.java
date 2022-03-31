package com.vorobeyyyyyy.openchat.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
@OpenAPIDefinition(info = @Info(title = "OpenChat API", version = "1.0"))
@SecurityScheme(name = HttpHeaders.AUTHORIZATION, type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER, scheme = "Bearer")
public class SwaggerConfig {
}
