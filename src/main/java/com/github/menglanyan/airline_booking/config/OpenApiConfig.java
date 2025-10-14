package com.github.menglanyan.airline_booking.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.PathItem;
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
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }

    @Bean
    public OpenApiCustomizer customizeSecurityForEndpoints() {
        return openApi -> {
            if (openApi.getPaths() == null) return;

            openApi.getPaths().forEach((path, item) ->
                item.readOperationsMap().forEach((method, op) -> {
                    boolean isAuth = path.startsWith("/api/auth/");

                    boolean isFlightsPublicGet =
                        method == PathItem.HttpMethod.GET
                            && (path.equals("/api/flights") || path.startsWith("/api/flights/"))
                            // except these protected flight endpoints:
                            && !path.equals("/api/flights/my-flights")
                            && !path.startsWith("/api/flights/my-flights/");

                    boolean isAirportsPublicGet =
                        method == PathItem.HttpMethod.GET
                            && (path.equals("/api/airports") || path.startsWith("/api/airports/"));

                    if (isAuth || isFlightsPublicGet || isAirportsPublicGet) {
                        op.setSecurity(java.util.Collections.emptyList());
                    }
                })
            );
        };
    }
}