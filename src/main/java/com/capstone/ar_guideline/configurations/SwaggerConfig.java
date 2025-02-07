package com.capstone.ar_guideline.configurations;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  private final String LOCALHOST = "http://localhost:8086/";

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("API-service document")
                .version("v1.0.0")
                .description("Description")
                .license(new License().name("Api License").url("http://domain.vn/license")))
        .servers(List.of(new Server().url(LOCALHOST).description("Tortee Server")))
        .components(
            new Components()
                .addSecuritySchemes(
                    "bearerAuth",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")))
        .security(List.of(new SecurityRequirement().addList("bearerAuth")));
  }

  @Bean
  public GroupedOpenApi groupedOpenApi() {
    return GroupedOpenApi.builder()
        .group("api-service")
        .packagesToScan("com.capstone.ar_guideline.controllers")
        .build();
  }
}
