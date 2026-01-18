package com.sb.framework.aiserver

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "ai-server")
data class AiServerProperties(
    val baseUrl: String
)