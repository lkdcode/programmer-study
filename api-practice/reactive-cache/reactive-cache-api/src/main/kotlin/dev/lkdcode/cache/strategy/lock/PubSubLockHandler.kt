package dev.lkdcode.cache.strategy.lock

import dev.lkdcode.cache.service.CacheService
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@Component
class PubSubLockHandler(
    private val cacheService: CacheService,
) : CacheLockHandler {

    override fun handleMonoCacheMiss(
        primaryKey: String,
        executeAndSave: () -> Mono<Any>,
        readFromCache: () -> Mono<Any>,
    ): Mono<Any> =
        cacheService
            .tryLock(primaryKey, LOCK_TTL)
            .flatMap { acquired ->
                if (acquired) {
                    executeAndSave()
                        .flatMap { value -> publishAndUnlock(primaryKey).thenReturn(value) }
                        .onErrorResume { e -> unlock(primaryKey).then(Mono.error(e)) }
                } else {
                    cacheService
                        .subscribeCacheReady(primaryKey, SUBSCRIBE_TIMEOUT)
                        .flatMap { readFromCache() }
                        .switchIfEmpty(Mono.defer { executeAndSave() })
                        .onErrorResume { executeAndSave() }
                }
            }

    override fun handleFluxCacheMiss(
        primaryKey: String,
        executeAndSave: () -> Flux<Any>,
        readFromCache: () -> Mono<Any>,
    ): Flux<Any> =
        cacheService
            .tryLock(primaryKey, LOCK_TTL)
            .flatMapMany { acquired ->
                if (acquired) {
                    executeAndSave()
                        .collectList()
                        .flatMap { items -> publishAndUnlock(primaryKey).thenReturn(items) }
                        .onErrorResume { e -> unlock(primaryKey).then(Mono.error(e)) }
                        .flatMapMany { Flux.fromIterable(it) }
                } else {
                    cacheService
                        .subscribeCacheReady(primaryKey, SUBSCRIBE_TIMEOUT)
                        .flatMap { readFromCache() }
                        .flatMapMany { toFlux(it) }
                        .switchIfEmpty(Flux.defer { executeAndSave() })
                        .onErrorResume { executeAndSave() }
                }
            }

    override fun refreshInBackground(
        primaryKey: String,
        executeAndSave: () -> Mono<Any>,
    ): Mono<Void> =
        cacheService
            .tryLock(primaryKey, LOCK_TTL)
            .filter { it }
            .flatMap {
                executeAndSave()
                    .flatMap { value -> publishAndUnlock(primaryKey).thenReturn(value) }
                    .onErrorResume { e -> unlock(primaryKey).then(Mono.error(e)) }
            }
            .then()

    private fun publishAndUnlock(primaryKey: String): Mono<Void> =
        publishCacheReady(primaryKey)
            .then(unlock(primaryKey))

    private fun unlock(primaryKey: String): Mono<Void> =
        cacheService
            .unlock(primaryKey)
            .onErrorResume { Mono.empty() }
            .then()

    private fun publishCacheReady(primaryKey: String): Mono<Void> =
        cacheService
            .publishCacheReady(primaryKey)
            .onErrorResume { Mono.empty() }
            .then()

    private fun toFlux(cached: Any): Flux<Any> =
        when (cached) {
            is List<*> -> Flux.fromIterable(cached.filterNotNull().map { it })
            else -> Flux.just(cached)
        }

    companion object {
        private val LOCK_TTL = Duration.ofSeconds(10L)
        private val SUBSCRIBE_TIMEOUT = Duration.ofSeconds(5L)
    }
}