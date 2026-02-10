package dev.lkdcode.cache.strategy.lock

import dev.lkdcode.cache.service.CacheService
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@Component
class SpinLockHandler(
    private val cacheService: CacheService,
) : CacheLockHandler {

    override fun handleMonoCacheMiss(
        primaryKey: String,
        executeAndSave: () -> Mono<Any>,
        readFromCache: () -> Mono<Any>,
    ): Mono<Any> =
        cacheService
            .tryLock(primaryKey, LOCK_TTL)
            .flatMap { token ->
                executeAndSave()
                    .flatMap { value -> unlock(primaryKey, token).thenReturn(value) }
                    .onErrorResume { e -> unlock(primaryKey, token).then(Mono.error(e)) }
            }
            .switchIfEmpty(Mono.defer {
                spinRetryMono(primaryKey, executeAndSave, readFromCache)
            })

    private fun spinRetryMono(
        primaryKey: String,
        executeAndSave: () -> Mono<Any>,
        readFromCache: () -> Mono<Any>,
        retryCount: Int = 0,
    ): Mono<Any> =
        Mono
            .delay(RETRY_DELAY)
            .flatMap { readFromCache() }
            .switchIfEmpty(
                Mono.defer {
                    if (retryCount < MAX_RETRY) {
                        spinRetryMono(primaryKey, executeAndSave, readFromCache, retryCount + 1)
                    } else {
                        executeAndSave()
                    }
                }
            )

    override fun handleFluxCacheMiss(
        primaryKey: String,
        executeAndSave: () -> Flux<Any>,
        readFromCache: () -> Mono<Any>,
    ): Flux<Any> =
        cacheService
            .tryLock(primaryKey, LOCK_TTL)
            .flatMapMany { token ->
                executeAndSave()
                    .collectList()
                    .flatMap { list -> unlock(primaryKey, token).thenReturn(list) }
                    .onErrorResume { e -> unlock(primaryKey, token).then(Mono.error(e)) }
                    .flatMapMany { Flux.fromIterable(it) }
            }
            .switchIfEmpty(Flux.defer {
                spinRetryFlux(primaryKey, executeAndSave, readFromCache)
            })

    private fun spinRetryFlux(
        primaryKey: String,
        executeAndSave: () -> Flux<Any>,
        readFromCache: () -> Mono<Any>,
        retryCount: Int = 0,
    ): Flux<Any> =
        Mono
            .delay(RETRY_DELAY)
            .flatMap { readFromCache() }
            .flatMapMany { toFlux(it) }
            .switchIfEmpty(
                Flux.defer {
                    if (retryCount < MAX_RETRY) {
                        spinRetryFlux(primaryKey, executeAndSave, readFromCache, retryCount + 1)
                    } else {
                        executeAndSave()
                    }
                }
            )

    override fun refreshInBackground(
        primaryKey: String,
        executeAndSave: () -> Mono<Any>,
    ): Mono<Void> =
        cacheService
            .tryLock(primaryKey, LOCK_TTL)
            .flatMap { token ->
                executeAndSave()
                    .flatMap { unlock(primaryKey, token).thenReturn(it) }
                    .onErrorResume { e -> unlock(primaryKey, token).then(Mono.error(e)) }
            }
            .then()

    private fun unlock(primaryKey: String, token: String): Mono<Void> =
        cacheService
            .unlock(primaryKey, token)
            .onErrorResume { Mono.empty() }
            .then()

    private fun toFlux(cached: Any): Flux<Any> =
        when (cached) {
            is List<*> -> Flux.fromIterable(cached.filterNotNull().map { it })
            else -> Flux.just(cached)
        }

    companion object {
        private val LOCK_TTL = Duration.ofSeconds(10L)
        private val RETRY_DELAY = Duration.ofMillis(50L)
        private const val MAX_RETRY = 20
    }
}
