package dev.lkdcode.cache.aspect

import dev.lkdcode.cache.annotation.ReactiveCacheable
import dev.lkdcode.cache.handler.ReactiveCacheConditionHandler
import dev.lkdcode.cache.handler.ReactiveCachePropertyHandler
import dev.lkdcode.cache.service.CacheService
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
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

    companion object {
        private val LOCK_TTL = Duration.ofSeconds(10L)
        private val RETRY_DELAY = Duration.ofMillis(50L)
        private const val MAX_RETRY = 20
    }

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
            .onErrorResume { Mono.empty() }
            .switchIfEmpty(Mono.defer { fetchWithLock(cacheKey, ttl, unless, joinPoint) })

    private fun fetchWithLock(
        cacheKey: String,
        ttl: Duration,
        unless: String,
        joinPoint: ProceedingJoinPoint
    ): Mono<Any> =
        cacheService.tryLock(cacheKey, LOCK_TTL)
            .flatMap { acquired ->
                if (acquired) {
                    executeAndCache(cacheKey, ttl, unless, joinPoint)
                        .doFinally { cacheService.unlock(cacheKey).subscribe() }
                } else {
                    waitAndRetryCache(cacheKey, ttl, unless, joinPoint)
                }
            }

    private fun executeAndCache(
        cacheKey: String,
        ttl: Duration,
        unless: String,
        joinPoint: ProceedingJoinPoint
    ): Mono<Any> {
        val result = joinPoint.proceed() as Mono<*>
        return result.flatMap { value ->
            if (conditionHandler.shouldCacheResult(value, unless, joinPoint)) {
                cacheService.save(cacheKey, value as Any, ttl)
                    .onErrorResume { Mono.empty() }
                    .thenReturn(value)
            } else {
                Mono.just(value as Any)
            }
        }
    }

    private fun waitAndRetryCache(
        cacheKey: String,
        ttl: Duration,
        unless: String,
        joinPoint: ProceedingJoinPoint,
        retryCount: Int = 0
    ): Mono<Any> =
        Mono.delay(RETRY_DELAY)
            .flatMap { cacheService.getValue(cacheKey).onErrorResume { Mono.empty() } }
            .switchIfEmpty(
                Mono.defer {
                    if (retryCount < MAX_RETRY) {
                        waitAndRetryCache(cacheKey, ttl, unless, joinPoint, retryCount + 1)
                    } else {
                        executeAndCache(cacheKey, ttl, unless, joinPoint)
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
            .switchIfEmpty(Flux.defer { fetchFluxWithLock(cacheKey, ttl, unless, joinPoint) })

    private fun fetchFluxWithLock(
        cacheKey: String,
        ttl: Duration,
        unless: String,
        joinPoint: ProceedingJoinPoint
    ): Flux<Any> =
        cacheService.tryLock(cacheKey, LOCK_TTL)
            .flatMapMany { acquired ->
                if (acquired) {
                    executeFluxAndCache(cacheKey, ttl, unless, joinPoint)
                        .doFinally { cacheService.unlock(cacheKey).subscribe() }
                } else {
                    waitAndRetryFluxCache(cacheKey, ttl, unless, joinPoint)
                }
            }

    private fun executeFluxAndCache(
        cacheKey: String,
        ttl: Duration,
        unless: String,
        joinPoint: ProceedingJoinPoint
    ): Flux<Any> {
        val result = joinPoint.proceed() as Flux<*>
        return result
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
            .flatMapMany { Flux.fromIterable(it as List<Any>) }
    }

    private fun waitAndRetryFluxCache(
        cacheKey: String,
        ttl: Duration,
        unless: String,
        joinPoint: ProceedingJoinPoint,
        retryCount: Int = 0
    ): Flux<Any> =
        Mono.delay(RETRY_DELAY)
            .flatMap { cacheService.getValue(cacheKey).onErrorResume { Mono.empty() } }
            .flatMapMany { cached ->
                when (cached) {
                    is List<*> -> Flux.fromIterable(cached)
                    else -> Flux.just(cached as Any)
                }
            }
            .switchIfEmpty(
                Flux.defer {
                    if (retryCount < MAX_RETRY) {
                        waitAndRetryFluxCache(cacheKey, ttl, unless, joinPoint, retryCount + 1)
                    } else {
                        executeFluxAndCache(cacheKey, ttl, unless, joinPoint)
                    }
                }
            )
}