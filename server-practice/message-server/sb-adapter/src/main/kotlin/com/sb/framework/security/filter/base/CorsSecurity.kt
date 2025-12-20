package com.sb.framework.security.filter.base

import org.springframework.stereotype.Component
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource


@Component
class CorsSecurity(
    private val allowedOrigin: AllowedOrigin
) {
    fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration()
            .apply {
                allowedOrigins = allowedOrigin.getList()
                allowedHeaders = ALLOWED_HEADERS
                allowedMethods = ALLOWED_METHODS
                exposedHeaders = EXPOSED_HEADERS
            }

        return UrlBasedCorsConfigurationSource()
            .apply {
                registerCorsConfiguration("/**", config)
            }
    }

    companion object {
        private val ALLOWED_HEADERS = listOf("*")
        private val ALLOWED_METHODS = listOf("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "UPGRADE")
        private val EXPOSED_HEADERS = listOf("Set-Cookie", "Authorization")
    }
}