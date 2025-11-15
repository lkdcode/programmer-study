package dev.lkdcode.infrastructure.websocket.session

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Sinks
import java.util.concurrent.ConcurrentHashMap


object SessionSinks {
    private val REPOSITORY = ConcurrentHashMap<String, Sinks.Many<String>>()

    fun init(sessionId: String) {
        REPOSITORY[sessionId] = Sinks.many().multicast().onBackpressureBuffer()
    }

    fun remove(sessionId: String) {
        REPOSITORY.remove(sessionId)
    }

    fun tryEmit(sessionId: String, message: String): Mono<Void> =
        Mono
            .fromRunnable {
                REPOSITORY[sessionId]?.tryEmitNext(message)
            }

    fun asFlux(sessionId: String): Flux<String> =
        REPOSITORY[sessionId]?.asFlux() ?: Flux.empty()

}