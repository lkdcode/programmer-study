package dev.lkdcode.cache.strategy.refresh

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

interface CacheRefreshHandler {
    fun readMono(
        primaryKey: String,
        softTtlMillis: Long,
        onCacheMiss: () -> Mono<Any>,
        onStale: () -> Mono<Void>,
        onError: () -> Mono<Any>,
    ): Mono<*>

    fun readFlux(
        primaryKey: String,
        softTtlMillis: Long,
        onCacheMiss: () -> Flux<Any>,
        onStale: () -> Mono<Void>,
        onError: () -> Flux<Any>,
    ): Flux<*>

    fun readFromPrimary(primaryKey: String): Mono<Any>

    fun save(key: String, value: Any, ttl: Duration): Mono<Void>
}
