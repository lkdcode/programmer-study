package dev.lkdcode.framework.config


import dev.lkdcode.infrastructure.websocket.SocketHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter

@Configuration
class WebSocketConfig {

    @Bean
    fun webSocketHandlerMapping(handler: SocketHandler): HandlerMapping =
        SimpleUrlHandlerMapping()
            .apply {
                order = 1
                urlMap = mapOf<String, WebSocketHandler>("/app/ws" to handler)
            }

    @Bean
    fun handlerAdapter(): WebSocketHandlerAdapter = WebSocketHandlerAdapter()
}