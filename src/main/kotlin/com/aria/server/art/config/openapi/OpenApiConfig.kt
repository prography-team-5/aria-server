package com.aria.server.art.config.openapi

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class OpenApiConfig {
    @Bean
    fun openAPI(): OpenAPI {
        val securitySchemeName = "bearerAuth"
        val info = Info()
            .title("Aria API")
            .version("v1.0.0")
            .description("Aria API Document")
        return OpenAPI()
            .addSecurityItem(
                SecurityRequirement()
                    .addList(securitySchemeName)
            )
            .components(
                Components()
                    .addSecuritySchemes(
                        securitySchemeName, SecurityScheme()
                            .name(securitySchemeName)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
            )
            .info(info)
    }
}