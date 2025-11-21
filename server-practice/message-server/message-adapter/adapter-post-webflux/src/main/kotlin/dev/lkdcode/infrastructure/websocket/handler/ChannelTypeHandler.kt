package dev.lkdcode.infrastructure.websocket.handler

import com.fasterxml.jackson.databind.ObjectMapper
import dev.lkdcode.infrastructure.security.UserAuthentication
import dev.lkdcode.infrastructure.websocket.enum.ChannelType
import dev.lkdcode.infrastructure.websocket.request.SocketRequest
import dev.lkdcode.infrastructure.websocket.session.SessionChannels
import dev.lkdcode.infrastructure.websocket.session.SessionSinks
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono


@Service
class ChannelTypeHandler(
    private val objectMapper: ObjectMapper,

    private val webSocketErrorHandler: WebSocketErrorHandler
) {

    fun input(session: WebSocketSession): Mono<Void> {
        return session
            .receive()
            .map(WebSocketMessage::getPayloadAsText)

            .flatMap {
                val message = objectMapper.readValue(it, SocketRequest::class.java)
                val sessionId = session.id
                val authentication = session.extractAuth()

                authentication
                    .flatMap { auth ->
                        when (message.channelType) {
                            ChannelType.DM -> Mono.just("DM")
                            ChannelType.GM -> Mono.just("GM")
                        }
                    }
                    .map { payload -> objectMapper.writeValueAsString(payload) }
                    .flatMap { converted -> session.send(Mono.just(session.textMessage(converted))) }
                    .onErrorResume(webSocketErrorHandler.handleError(session))
            }

            .doFinally {
                SessionSinks.remove(session.id)
                SessionChannels.remove(session.id)
            }
            .then()
    }

    private fun WebSocketSession.extractAuth() =
        this.handshakeInfo
            .principal
            .cast(UsernamePasswordAuthenticationToken::class.java)
            .map { it.principal }
            .cast(UserAuthentication::class.java)
}