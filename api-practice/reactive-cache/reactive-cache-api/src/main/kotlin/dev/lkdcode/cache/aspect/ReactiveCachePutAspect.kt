package dev.lkdcode.cache.aspect

import dev.lkdcode.cache.annotation.ReactiveCachePut
import dev.lkdcode.cache.handler.CacheProperty
import dev.lkdcode.cache.handler.ReactiveCacheConditionHandler
import dev.lkdcode.cache.handler.ReactiveCachePropertyHandler
import dev.lkdcode.cache.strategy.RefreshStrategyResolver
import dev.lkdcode.cache.strategy.refresh.CacheRefreshHandler
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Aspect
@Component
class ReactiveCachePutAspect(
    private val conditionHandler: ReactiveCacheConditionHandler,
    private val reactiveCachePropertyHandler: ReactiveCachePropertyHandler,
    private val refreshStrategyResolver: RefreshStrategyResolver,
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Around("@annotation(reactiveCachePut)")
    fun handleCachePut(joinPoint: ProceedingJoinPoint, reactiveCachePut: ReactiveCachePut): Any {
        if (conditionHandler.shouldNotCache(joinPoint, reactiveCachePut.condition)) return joinPoint.proceed()
        val prop = reactiveCachePropertyHandler.cacheProperty(joinPoint, reactiveCachePut)
        val refreshHandler = refreshStrategyResolver.resolve(prop.refreshStrategy)

        return when (val result = joinPoint.proceed()) {
            is Mono<*> -> handleMonoCachePut(result, prop, refreshHandler, joinPoint)
            is Flux<*> -> handleFluxCachePut(result, prop, refreshHandler, joinPoint)
            else -> {
                logger.warn(
                    "[ReactiveCachePut] Mono 또는 Flux가 아닌 반환 타입에는 적용되지 않습니다. " +
                    "메서드: {}, 반환 타입: {}",
                    joinPoint.signature.toShortString(),
                    result?.javaClass?.simpleName
                )
                result
            }
        }
    }

    private fun handleMonoCachePut(
        result: Mono<*>,
        prop: CacheProperty,
        refreshHandler: CacheRefreshHandler,
        joinPoint: ProceedingJoinPoint,
    ): Mono<*> =
        result.flatMap { value ->
            if (conditionHandler.shouldCacheResult(value, prop.unless, joinPoint)) {
                saveToAllKeys(prop, refreshHandler, value as Any).thenReturn(value)
            } else {
                Mono.just(value)
            }
        }

    private fun handleFluxCachePut(
        result: Flux<*>,
        prop: CacheProperty,
        refreshHandler: CacheRefreshHandler,
        joinPoint: ProceedingJoinPoint,
    ): Flux<*> =
        result
            .collectList()
            .flatMap { list ->
                if (conditionHandler.shouldCacheResult(list, prop.unless, joinPoint)) {
                    saveToAllKeys(prop, refreshHandler, list as Any).thenReturn(list)
                } else {
                    Mono.just(list)
                }
            }
            .flatMapMany { Flux.fromIterable(it) }

    private fun saveToAllKeys(prop: CacheProperty, refreshHandler: CacheRefreshHandler, value: Any): Mono<Void> =
        Flux.fromIterable(prop.cacheKeys)
            .flatMap { key -> refreshHandler.save(key, value, prop.ttl) }
            .then()
}
