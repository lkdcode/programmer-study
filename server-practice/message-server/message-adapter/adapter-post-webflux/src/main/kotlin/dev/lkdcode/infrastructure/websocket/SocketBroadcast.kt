package dev.lkdcode.infrastructure.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import dev.lkdcode.infrastructure.websocket.session.SessionChannels
import dev.lkdcode.infrastructure.websocket.session.SessionSinks
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class SocketBroadcast(
    private val objectMapper: ObjectMapper,
) {

    fun init(sessionId: String) {
        SessionChannels.init(sessionId)
        SessionSinks.init(sessionId)
    }

    fun toSession(channelKey: String, msg: Any): Mono<Void> =
        SessionChannels
            .getSessionId(channelKey)
            .flatMap {
                SessionSinks.tryEmit(it, objectMapper.writeValueAsString(msg))
            }
            .then()
}