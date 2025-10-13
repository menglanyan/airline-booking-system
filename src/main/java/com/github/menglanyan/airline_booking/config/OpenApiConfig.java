package com.github.menglanyan.airline_booking.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }

    @Bean
    public OpenApiCustomizer customizeSecurityForEndpoints() {
        return openApi -> {
            if (openApi.getPaths() == null) return;

            openApi.getPaths().forEach((path, pathItem) -> {
                boolean isPublicEndpoint =
                        path.startsWith("/api/auth/") ||
                                path.equals("/api/airports") || path.startsWith("/api/airports/") ||
                                path.equals("/api/flights") || path.startsWith("/api/flights/");

                // Apply JWT security to everything except these public URLs
                if (!isPublicEndpoint && pathItem != null) {
                    pathItem.readOperations().forEach(op ->
                            op.addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                    );
                }
            });
        };
    }
}