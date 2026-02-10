package dev.lkdcode.cache.aspect

import dev.lkdcode.cache.annotation.ReactiveCacheable
import dev.lkdcode.cache.handler.CacheProperty
import dev.lkdcode.cache.handler.ReactiveCacheConditionHandler
import dev.lkdcode.cache.handler.ReactiveCachePropertyHandler
import dev.lkdcode.cache.strategy.LockStrategyResolver
import dev.lkdcode.cache.strategy.RefreshStrategyResolver
import dev.lkdcode.cache.strategy.lock.CacheLockHandler
import dev.lkdcode.cache.strategy.refresh.CacheRefreshHandler
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Aspect
@Component
class ReactiveCacheableAspect(
    private val conditionHandler: ReactiveCacheConditionHandler,
    private val propertyHandler: ReactiveCachePropertyHandler,
    private val lockStrategyResolver: LockStrategyResolver,
    private val refreshStrategyResolver: RefreshStrategyResolver,
) {

    @Around("@annotation(reactiveCacheable)")
    fun handleCacheable(joinPoint: ProceedingJoinPoint, reactiveCacheable: ReactiveCacheable): Any {
        if (conditionHandler.shouldNotCache(joinPoint, reactiveCacheable.condition)) return joinPoint.proceed()
        val prop = propertyHandler.cacheProperty(joinPoint, reactiveCacheable)
        val primaryKey = prop.cacheKeys.first()

        val lockHandler = lockStrategyResolver.resolve(prop.lockStrategy)
        val refreshHandler = refreshStrategyResolver.resolve(prop.refreshStrategy)

        val returnType = (joinPoint.signature as MethodSignature).returnType

        return when {
            Mono::class.java.isAssignableFrom(returnType) -> handleMono(primaryKey, prop, lockHandler, refreshHandler, joinPoint)
            Flux::class.java.isAssignableFrom(returnType) -> handleFlux(primaryKey, prop, lockHandler, refreshHandler, joinPoint)
            else -> joinPoint.proceed()
        }
    }

    private fun handleMono(
        primaryKey: String,
        prop: CacheProperty,
        lockHandler: CacheLockHandler,
        refreshHandler: CacheRefreshHandler,
        joinPoint: ProceedingJoinPoint,
    ): Mono<*> =
        refreshHandler.readMono(
            primaryKey = primaryKey,
            softTtlMillis = prop.softTtl.toMillis(),
            onCacheMiss = {
                lockHandler.handleMonoCacheMiss(
                    primaryKey = primaryKey,
                    executeAndSave = { executeAndSaveMono(prop, refreshHandler, joinPoint) },
                    readFromCache = { refreshHandler.readFromPrimary(primaryKey) },
                )
            },
            onStale = {
                lockHandler.refreshInBackground(primaryKey) {
                    backgroundRefreshAction(prop, refreshHandler, joinPoint)
                }
            },
            onError = { (joinPoint.proceed() as Mono<*>).map { it as Any } },
        )

    private fun handleFlux(
        primaryKey: String,
        prop: CacheProperty,
        lockHandler: CacheLockHandler,
        refreshHandler: CacheRefreshHandler,
        joinPoint: ProceedingJoinPoint,
    ): Flux<*> =
        refreshHandler.readFlux(
            primaryKey = primaryKey,
            softTtlMillis = prop.softTtl.toMillis(),
            onCacheMiss = {
                lockHandler.handleFluxCacheMiss(
                    primaryKey = primaryKey,
                    executeAndSave = { executeAndSaveFlux(prop, refreshHandler, joinPoint) },
                    readFromCache = { refreshHandler.readFromPrimary(primaryKey) },
                )
            },
            onStale = {
                lockHandler.refreshInBackground(primaryKey) {
                    backgroundRefreshAction(prop, refreshHandler, joinPoint)
                }
            },
            onError = { (joinPoint.proceed() as Flux<*>).map { it as Any } },
        )

    private fun executeAndSaveMono(
        prop: CacheProperty,
        refreshHandler: CacheRefreshHandler,
        joinPoint: ProceedingJoinPoint,
    ): Mono<Any> {
        val result = joinPoint.proceed() as Mono<*>
        return result.flatMap { value -> saveAndReturn(prop, refreshHandler, value as Any, joinPoint) }
    }

    private fun executeAndSaveFlux(
        prop: CacheProperty,
        refreshHandler: CacheRefreshHandler,
        joinPoint: ProceedingJoinPoint,
    ): Flux<Any> {
        val result = joinPoint.proceed() as Flux<*>
        return result
            .collectList()
            .flatMap { list -> saveAndReturn(prop, refreshHandler, list as Any, joinPoint) }
            .flatMapMany { Flux.fromIterable(it as List<*>) }
    }

    private fun backgroundRefreshAction(
        prop: CacheProperty,
        refreshHandler: CacheRefreshHandler,
        joinPoint: ProceedingJoinPoint,
    ): Mono<Any> {
        val returnType = (joinPoint.signature as MethodSignature).returnType
        return when {
            Mono::class.java.isAssignableFrom(returnType) -> executeAndSaveMono(prop, refreshHandler, joinPoint)
            Flux::class.java.isAssignableFrom(returnType) ->
                executeAndSaveFlux(prop, refreshHandler, joinPoint)
                    .collectList()
                    .map { it as Any }
            else -> Mono.just(joinPoint.proceed() as Any)
        }
    }

    private fun saveAndReturn(
        prop: CacheProperty,
        refreshHandler: CacheRefreshHandler,
        value: Any,
        joinPoint: ProceedingJoinPoint,
    ): Mono<Any> =
        if (conditionHandler.shouldCacheResult(value, prop.unless, joinPoint)) {
            Flux.fromIterable(prop.cacheKeys)
                .flatMap { key -> refreshHandler.save(key, value, prop.ttl) }
                .then(Mono.just(value))
        } else {
            Mono.just(value)
        }
}
