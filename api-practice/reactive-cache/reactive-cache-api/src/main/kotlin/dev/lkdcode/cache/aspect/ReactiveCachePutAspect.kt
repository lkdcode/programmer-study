package dev.lkdcode.cache.aspect

import dev.lkdcode.cache.annotation.ReactiveCachePut
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
class ReactiveCachePutAspect(
    private val cacheService: CacheService,
    private val conditionHandler: ReactiveCacheConditionHandler,
    private val reactiveCachePropertyHandler: ReactiveCachePropertyHandler,
) {

    @Around("@annotation(reactiveCachePut)")
    fun handleCachePut(joinPoint: ProceedingJoinPoint, reactiveCachePut: ReactiveCachePut): Any {
        if (conditionHandler.shouldNotCache(joinPoint, reactiveCachePut.condition)) return joinPoint.proceed()
        val (cacheKey, ttl, unless) = reactiveCachePropertyHandler.cacheProperty(joinPoint, reactiveCachePut)

        return when (val result = joinPoint.proceed()) {
            is Mono<*> -> handleMonoCachePut(result, cacheKey, ttl, unless, joinPoint)
            is Flux<*> -> handleFluxCachePut(result, cacheKey, ttl, unless, joinPoint)
            else -> result
        }
    }

    private fun handleMonoCachePut(
        result: Mono<*>,
        cacheKey: String,
        ttl: Duration,
        unless: String,
        joinPoint: ProceedingJoinPoint
    ): Mono<*> {
        return result.flatMap { value ->
            if (conditionHandler.shouldCacheResult(value, unless, joinPoint)) {
                cacheService.save(cacheKey, value as Any, ttl).thenReturn(value)
            } else {
                Mono.just(value)
            }
        }
    }

    private fun handleFluxCachePut(
        result: Flux<*>,
        cacheKey: String,
        ttl: Duration,
        unless: String,
        joinPoint: ProceedingJoinPoint
    ): Flux<*> {
        return result
            .collectList()
            .flatMap { list ->
                if (conditionHandler.shouldCacheResult(list, unless, joinPoint)) {
                    cacheService.save(cacheKey, list as Any, ttl).thenReturn(list)
                } else {
                    Mono.just(list)
                }
            }
            .flatMapMany { Flux.fromIterable(it) }
    }

}