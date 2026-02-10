package dev.lkdcode.cache.strategy.lock

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CacheLockHandler {
    fun handleMonoCacheMiss(
        primaryKey: String,
        executeAndSave: () -> Mono<Any>,
        readFromCache: () -> Mono<Any>,
    ): Mono<Any>

    fun handleFluxCacheMiss(
        primaryKey: String,
        executeAndSave: () -> Flux<Any>,
        readFromCache: () -> Mono<Any>,
    ): Flux<Any>

    fun refreshInBackground(
        primaryKey: String,
        executeAndSave: () -> Mono<Any>,
    ): Mono<Void>
}
