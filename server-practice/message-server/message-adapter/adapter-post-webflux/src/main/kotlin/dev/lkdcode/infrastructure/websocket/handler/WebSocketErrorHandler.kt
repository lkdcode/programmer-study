package dev.lkdcode.infrastructure.websocket.handler

import com.fasterxml.jackson.databind.ObjectMapper
import dev.lkdcode.infrastructure.interaction.api.ApiException
import dev.lkdcode.infrastructure.websocket.response.SocketResponse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono


@Component
class WebSocketErrorHandler(
    private val objectMapper: ObjectMapper
) {

    fun <T> handleError(session: WebSocketSession): (Throwable) -> Mono<T> = { error ->
        val errorResponse = createErrorResponse<T>(error)
        val errorJson = objectMapper.writeValueAsString(errorResponse)

        session
            .send(Mono.just(session.textMessage(errorJson)))
            .doOnSuccess {
                println("✅ Error sent: ${errorResponse.code} - ${errorResponse.message}")
                println("${error.message}")
            }
            .then(Mono.empty<T>())
    }

    private fun <T> createErrorResponse(error: Throwable): SocketResponse<T> {
        return when (error) {
            is ApiException -> {
                SocketResponse(
                    success = false,
                    code = error.code(),
                    message = error.message()
                )
            }

            else -> {
                SocketResponse(
                    success = false,
                    code = "INVALID",
                    message = "INVALID"
                )
            }
        }
    }
}