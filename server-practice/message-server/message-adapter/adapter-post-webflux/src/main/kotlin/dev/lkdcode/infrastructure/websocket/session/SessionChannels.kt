package dev.lkdcode.infrastructure.websocket.session

import reactor.core.publisher.Flux
import java.util.concurrent.ConcurrentHashMap


object SessionChannels {
    private val REPOSITORY = ConcurrentHashMap<String, MutableSet<String>>()

    fun init(sessionId: String) {
        REPOSITORY[sessionId] = ConcurrentHashMap.newKeySet()
    }

    fun add(sessionId: String, channelKey: String) {
        REPOSITORY[sessionId]?.add(channelKey)
    }

    fun remove(sessionId: String) {
        REPOSITORY.remove(sessionId)
    }

    fun getSessionId(channelKey: String): Flux<String> =
        Flux
            .fromIterable(REPOSITORY.entries)
            .filter { (_, channels) -> channels.contains(channelKey) }
            .map { (sessionId, _) -> sessionId }

}