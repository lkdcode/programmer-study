package com.sb.framework.docs

import com.fasterxml.jackson.databind.ObjectMapper
import com.sb.framework.security.jwt.spec.JwtSpec
import io.swagger.v3.core.jackson.ModelResolver
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("dev", "local")
@Configuration
class ApiDocsConfig {

    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
        .info(
            Info()
                .title("\uD83D\uDD8C\uFE0F 2025-SoomBoot")
                .summary("🚀 SoomBoot")
                .description(
                    """
                    - 🎯 Work
                """.trimIndent()
                )
                .contact(
                    Contact().apply {
                        name = "🐈‍⬛ Github 바로가기"
                        url = "https://github.com/lkdcode"
                    }
                )
        )
        .components(
            Components().addSecuritySchemes(
                "SoomBoot DEV JWT",
                SecurityScheme().apply {
                    name = JwtSpec.TOKEN_HEADER_KEY
                    type = SecurityScheme.Type.HTTP
                    scheme = "bearer"
                    bearerFormat = "JWT"
                    `in` = SecurityScheme.In.HEADER
                }
            )
        )
        .addSecurityItem(
            SecurityRequirement().addList("SoomBoot DEV JWT")
        )

    @Bean
    fun modelResolver(objectMapper: ObjectMapper): ModelResolver =
        ModelResolver(objectMapper)
}