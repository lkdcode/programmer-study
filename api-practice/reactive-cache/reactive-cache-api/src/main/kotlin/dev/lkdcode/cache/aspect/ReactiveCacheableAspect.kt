package dev.lkdcode.cache.aspect

import dev.lkdcode.cache.annotation.ReactiveCacheable
import dev.lkdcode.cache.handler.ReactiveCacheConditionHandler
import dev.lkdcode.cache.handler.ReactiveCachePropertyHandler
import dev.lkdcode.cache.service.CacheService
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
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
    private val log = LoggerFactory.getLogger(ReactiveCacheableAspect::class.java)

    @Around("@annotation(reactiveCacheable)")
    fun handleCacheable(joinPoint: ProceedingJoinPoint, reactiveCacheable: ReactiveCacheable): Any {
        if (conditionHandler.shouldNotCache(joinPoint, reactiveCacheable.condition)) return joinPoint.proceed()
        val (cacheKey, ttl, unless) = reactiveCachePropertyHandler.cacheProperty(joinPoint, reactiveCacheable)

        val methodSignature = joinPoint.signature as MethodSignature
        val returnType = methodSignature.returnType

        return when {
            Mono::class.java.isAssignableFrom(returnType) -> handleMonoCacheable(cacheKey, ttl, unless, joinPoint)
            Flux::class.java.isAssignableFrom(returnType) -> handleFluxCacheable(cacheKey, ttl, unless, joinPoint)
            else -> joinPoint.proceed()
        }
    }

    private fun handleMonoCacheable(
        cacheKey: String,
        ttl: Duration,
        unless: String,
        joinPoint: ProceedingJoinPoint
    ): Mono<*> =
        cacheService
            .getValue(cacheKey)
            .flatMap { cached -> Mono.just(cached) }
            .onErrorResume { Mono.empty() }
            .switchIfEmpty(
                Mono.defer {
                    val result = joinPoint.proceed() as Mono<*>

                    result.flatMap { value ->
                        if (conditionHandler.shouldCacheResult(value, unless, joinPoint)) {
                            cacheService.save(cacheKey, value as Any, ttl)
                                .onErrorResume { Mono.empty() }
                                .thenReturn(value)
                        } else {
                            Mono.just(value)
                        }
                    }
                }
            )

    private fun handleFluxCacheable(
        cacheKey: String,
        ttl: Duration,
        unless: String,
        joinPoint: ProceedingJoinPoint
    ): Flux<*> =
        cacheService
            .getValue(cacheKey)
            .onErrorResume { Mono.empty() }
            .flatMapMany { cached ->
                when (cached) {
                    is List<*> -> Flux.fromIterable(cached)
                    else -> Flux.just(cached)
                }
            }
            .switchIfEmpty(
                Flux.defer {
                    val result = joinPoint.proceed() as Flux<*>

                    result
                        .collectList()
                        .flatMap { list ->
                            if (conditionHandler.shouldCacheResult(list, unless, joinPoint)) {
                                cacheService.save(cacheKey, list as Any, ttl)
                                    .onErrorResume { Mono.empty() }
                                    .thenReturn(list)
                            } else {
                                Mono.just(list)
                            }
                        }
                        .flatMapMany { Flux.fromIterable(it) }
                }
            )
}