package dev.lkdcode.cache.aspect

import dev.lkdcode.cache.annotation.ReactiveCacheEvict
import dev.lkdcode.cache.handler.ReactiveCacheConditionHandler
import dev.lkdcode.cache.handler.ReactiveCachePropertyHandler
import dev.lkdcode.cache.service.CacheService
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Aspect
@Component
class ReactiveCacheEvictAspect(
    private val cacheService: CacheService,
    private val conditionHandler: ReactiveCacheConditionHandler,
    private val reactiveCachePropertyHandler: ReactiveCachePropertyHandler,
) {
    private val log = LoggerFactory.getLogger(ReactiveCacheEvictAspect::class.java)

    @Around("@annotation(reactiveCacheEvict)")
    fun handleCacheEvict(joinPoint: ProceedingJoinPoint, reactiveCacheEvict: ReactiveCacheEvict): Any {
        val condition = reactiveCacheEvict.condition
        if (conditionHandler.shouldNotCache(joinPoint, condition)) {
            return joinPoint.proceed()
        }

        val (cacheKeyOrPrefix, isAllEntries) = reactiveCachePropertyHandler.cacheProperty(joinPoint, reactiveCacheEvict)

        val evictOperation = if (isAllEntries) {
            cacheService
                .deleteByPrefix(cacheKeyOrPrefix)
                .onErrorResume { Mono.just(0L) }
                .then()
        } else {
            cacheService
                .delete(cacheKeyOrPrefix)
                .onErrorResume { Mono.empty() }
        }

        return when (val result = joinPoint.proceed()) {
            is Mono<*> -> result.flatMap { evictOperation.thenReturn(it) }
            is Flux<*> -> result.collectList()
                .flatMap { list -> evictOperation.thenReturn(list) }
                .flatMapMany { Flux.fromIterable(it) }

            else -> result
        }
    }
}