package dev.lkdcode.cache.strategy.refresh

import dev.lkdcode.cache.service.CacheService
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@Component
class NoneRefreshHandler(
    private val cacheService: CacheService,
) : CacheRefreshHandler {

    override fun readMono(
        primaryKey: String,
        softTtlMillis: Long,
        onCacheMiss: () -> Mono<Any>,
        onStale: () -> Mono<Void>,
        onError: () -> Mono<Any>,
    ): Mono<*> =
        cacheService
            .getValue(primaryKey)
            .onErrorResume { Mono.empty() }
            .switchIfEmpty(Mono.defer { onCacheMiss() })

    override fun readFlux(
        primaryKey: String,
        softTtlMillis: Long,
        onCacheMiss: () -> Flux<Any>,
        onStale: () -> Mono<Void>,
        onError: () -> Flux<Any>,
    ): Flux<*> =
        cacheService
            .getValue(primaryKey)
            .onErrorResume { Mono.empty() }
            .flatMapMany { cached ->
                when (cached) {
                    is List<*> -> Flux.fromIterable(cached)
                    else -> Flux.just(cached)
                }
            }
            .switchIfEmpty(Flux.defer { onCacheMiss() })

    override fun readFromPrimary(primaryKey: String): Mono<Any> =
        cacheService
            .getValue(primaryKey)
            .onErrorResume { Mono.empty() }

    override fun save(key: String, value: Any, ttl: Duration): Mono<Void> =
        cacheService
            .save(key, value, ttl)
            .onErrorResume { Mono.empty() }
            .then()
}