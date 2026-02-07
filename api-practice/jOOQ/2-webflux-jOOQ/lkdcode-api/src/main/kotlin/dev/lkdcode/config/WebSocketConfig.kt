package dev.lkdcode.config

import dev.lkdcode.api.KiwiWebSocketHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping

@Configuration
class WebSocketConfig {

    @Bean
    fun webSocketHandlerMapping(handler: KiwiWebSocketHandler): HandlerMapping =
        SimpleUrlHandlerMapping(mapOf("/ws/kiwi" to handler), -1)
}