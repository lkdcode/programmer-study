package dev.lkdcode.cache.aspect

import dev.lkdcode.cache.annotation.ReactiveCacheable
import dev.lkdcode.cache.handler.ReactiveCacheConditionHandler
import dev.lkdcode.cache.handler.ReactiveCachePropertyHandler
import dev.lkdcode.cache.service.CacheService
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@Aspect
@Component
class ReactiveCacheableAspect(
    private val cacheService: CacheService,
    private val conditionHandler: ReactiveCacheConditionHandler,
    private val reactiveCachePropertyHandler: ReactiveCachePropertyHandler,
) {

    @Around("@annotation(reactiveCacheable)")
    fun handleCacheable(joinPoint: ProceedingJoinPoint, reactiveCacheable: ReactiveCacheable): Any {
        if (conditionHandler.shouldNotCache(joinPoint, reactiveCacheable.condition)) return joinPoint.proceed()
        val (cacheKey, ttl, unless) = reactiveCachePropertyHandler.cacheProperty(joinPoint, reactiveCacheable)

        return when (val result = joinPoint.proceed()) {
            is Mono<*> -> handleMonoCacheable(result, cacheKey, ttl, unless, joinPoint)
            is Flux<*> -> handleFluxCacheable(result, cacheKey, ttl, unless, joinPoint)
            else -> result
        }
    }

    private fun handleMonoCacheable(
        result: Mono<*>,
        cacheKey: String,
        ttl: Duration,
        unless: String,
        joinPoint: ProceedingJoinPoint
    ): Mono<*> {
        return cacheService.getValue(cacheKey)
            .flatMap { cached -> Mono.just(cached) }
            .switchIfEmpty(
                result.flatMap { value ->
                    if (conditionHandler.shouldCacheResult(value, unless, joinPoint)) {
                        cacheService.save(cacheKey, value as Any, ttl).thenReturn(value)
                    } else {
                        Mono.just(value)
                    }
                }
            )
    }

    private fun handleFluxCacheable(
        result: Flux<*>,
        cacheKey: String,
        ttl: Duration,
        unless: String,
        joinPoint: ProceedingJoinPoint
    ): Flux<*> {
        return cacheService.getValue(cacheKey)
            .flatMapMany { cached ->
                when (cached) {
                    is List<*> -> Flux.fromIterable(cached)
                    else -> Flux.just(cached)
                }
            }
            .switchIfEmpty(
                result.collectList()
                    .flatMap { list ->
                        if (conditionHandler.shouldCacheResult(list, unless, joinPoint)) {
                            cacheService.save(cacheKey, list as Any, ttl).thenReturn(list)
                        } else {
                            Mono.just(list)
                        }
                    }
                    .flatMapMany { Flux.fromIterable(it) }
            )
    }
}