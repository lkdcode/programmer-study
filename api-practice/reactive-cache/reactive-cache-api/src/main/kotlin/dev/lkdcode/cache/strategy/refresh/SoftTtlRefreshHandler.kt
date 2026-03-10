package dev.lkdcode.cache.strategy.refresh

import dev.lkdcode.cache.service.CacheService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.Duration

@Component
class SoftTtlRefreshHandler(
    private val cacheService: CacheService,
) : CacheRefreshHandler {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun readMono(
        primaryKey: String,
        softTtlMillis: Long,
        onCacheMiss: () -> Mono<Any>,
        onStale: () -> Mono<Void>,
        onError: () -> Mono<Any>,
    ): Mono<*> =
        cacheService
            .getValueWithTimestamp(primaryKey)
            .flatMap { cached ->
                if (cached.isStale(softTtlMillis)) {
                    triggerBackgroundRefresh(onStale)
                }
                Mono.just(cached.value)
            }
            .switchIfEmpty(Mono.defer { onCacheMiss() })
            .onErrorResume { onError() }

    override fun readFlux(
        primaryKey: String,
        softTtlMillis: Long,
        onCacheMiss: () -> Flux<Any>,
        onStale: () -> Mono<Void>,
        onError: () -> Flux<Any>,
    ): Flux<*> =
        cacheService
            .getValueWithTimestamp(primaryKey)
            .flatMapMany { cached ->
                if (cached.isStale(softTtlMillis)) {
                    triggerBackgroundRefresh(onStale)
                }
                when (val value = cached.value) {
                    is List<*> -> Flux.fromIterable(value.filterNotNull().map { it })
                    else -> Flux.just(value)
                }
            }
            .switchIfEmpty(Flux.defer { onCacheMiss() })
            .onErrorResume { onError() }

    override fun readFromPrimary(primaryKey: String): Mono<Any> =
        cacheService
            .getValueWithTimestamp(primaryKey)
            .map { it.value }
            .onErrorResume { Mono.empty() }

    override fun save(key: String, value: Any, ttl: Duration): Mono<Void> =
        cacheService
            .saveWithTimestamp(key, value, ttl)
            .onErrorResume { Mono.empty() }
            .then()

    private fun triggerBackgroundRefresh(onStale: () -> Mono<Void>) {
        onStale()
            .subscribeOn(Schedulers.boundedElastic())
            .onErrorResume { e ->
                logger.warn("[SoftTtlRefresh] 백그라운드 리프레시에 실패, 만료된 데이터 반환", e)
                Mono.empty()
            }
            .subscribe()
    }
}