package com.smartmenu.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI 3 / Swagger Documentation Configuration
 * Configures production and local server URLs and JWT Authorization for Swagger UI.
 */
@Configuration
public class OpenApiConfig {

    @Value("${app.api.base-url:https://api-smartmenu.techwareafrica.tech}")
    private String productionApiUrl;

    @Bean
    public OpenAPI smartMenuOpenAPI() {
        Server prodServer = new Server()
                .url(productionApiUrl)
                .description("Production API Server");

        Server localServer = new Server()
                .url("http://localhost:8080")
                .description("Local Development Server");

        final String securitySchemeName = "BearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("SmartMenu SaaS API")
                        .description("Cloud-native Digital QR Restaurant Menu & Order Management System API")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("TechWare Africa")
                                .url("https://smartmenu.techwareafrica.tech"))
                        .license(new License().name("Proprietary")))
                .servers(List.of(prodServer, localServer))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Enter your JWT Bearer token here (without 'Bearer ')")));
    }
}
