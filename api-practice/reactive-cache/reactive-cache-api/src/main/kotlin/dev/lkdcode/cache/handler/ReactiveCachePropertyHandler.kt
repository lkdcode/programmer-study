package dev.lkdcode.cache.handler

import dev.lkdcode.cache.annotation.ReactiveCacheEvict
import dev.lkdcode.cache.annotation.ReactiveCachePut
import dev.lkdcode.cache.annotation.ReactiveCacheable
import dev.lkdcode.cache.interceptor.ReactiveKeyGenerator
import dev.lkdcode.cache.parser.ReactiveCacheArgsParser
import dev.lkdcode.cache.strategy.LockStrategy
import dev.lkdcode.cache.strategy.RefreshStrategy
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import java.time.Duration

data class CacheProperty(
    val cacheKeys: List<String>,
    val ttl: Duration,
    val softTtl: Duration,
    val unless: String,
    val lockStrategy: LockStrategy,
    val refreshStrategy: RefreshStrategy,
)

@Component
class ReactiveCachePropertyHandler(
    private val applicationContext: ApplicationContext,
    private val reactiveCacheArgsParser: ReactiveCacheArgsParser,
) {

    fun cacheProperty(
        joinPoint: ProceedingJoinPoint,
        reactiveCacheable: ReactiveCacheable
    ): CacheProperty {
        val cacheKeys =
            resolveKeys(joinPoint, reactiveCacheable.keyGenerator, reactiveCacheable.key, reactiveCacheable.cacheNames)
        val ttl = Duration.ofSeconds(reactiveCacheable.ttl)
        val softTtl = resolveSoftTtl(reactiveCacheable.softTtl, ttl)

        return CacheProperty(
            cacheKeys = cacheKeys,
            ttl = ttl,
            softTtl = softTtl,
            unless = reactiveCacheable.unless,
            lockStrategy = reactiveCacheable.lockStrategy,
            refreshStrategy = reactiveCacheable.refreshStrategy,
        )
    }

    fun cacheProperty(
        joinPoint: ProceedingJoinPoint,
        reactiveCachePut: ReactiveCachePut
    ): CacheProperty {
        val cacheKeys =
            resolveKeys(joinPoint, reactiveCachePut.keyGenerator, reactiveCachePut.key, reactiveCachePut.cacheNames)
        val ttl = Duration.ofSeconds(reactiveCachePut.ttl)
        val softTtl = resolveSoftTtl(reactiveCachePut.softTtl, ttl)

        return CacheProperty(
            cacheKeys = cacheKeys,
            ttl = ttl,
            softTtl = softTtl,
            unless = reactiveCachePut.unless,
            lockStrategy = reactiveCachePut.lockStrategy,
            refreshStrategy = reactiveCachePut.refreshStrategy,
        )
    }

    fun cacheProperty(
        joinPoint: ProceedingJoinPoint,
        reactiveCacheEvict: ReactiveCacheEvict
    ): Pair<List<String>, Boolean> {
        return if (reactiveCacheEvict.allEntries) {
            Pair(resolvePrefixes(joinPoint, reactiveCacheEvict.cacheNames), true)
        } else {
            Pair(
                resolveKeys(
                    joinPoint,
                    reactiveCacheEvict.keyGenerator,
                    reactiveCacheEvict.key,
                    reactiveCacheEvict.cacheNames
                ), false
            )
        }
    }

    private fun resolveSoftTtl(softTtlSeconds: Long, ttl: Duration): Duration =
        if (softTtlSeconds < 0) ttl else Duration.ofSeconds(softTtlSeconds)

    private fun resolveKeys(
        joinPoint: ProceedingJoinPoint,
        keyGenerator: String,
        key: String,
        cacheNames: Array<String>
    ): List<String> {
        if (cacheNames.isEmpty()) {
            return listOf(resolveKey(joinPoint, keyGenerator, key))
        }

        val signature = joinPoint.signature as MethodSignature
        val keyValue = reactiveCacheArgsParser.keyParse(key, joinPoint.args, signature.parameterNames)

        return cacheNames.map { cacheName -> "$cacheName::$keyValue" }
    }

    private fun resolvePrefixes(joinPoint: ProceedingJoinPoint, cacheNames: Array<String>): List<String> {
        if (cacheNames.isEmpty()) return listOf(resolvePrefix(joinPoint))

        return cacheNames.map { "$it::" }
    }

    private fun resolvePrefix(joinPoint: ProceedingJoinPoint): String {
        val className = joinPoint.target.javaClass.simpleName
        val methodName = joinPoint.signature.name

        return "$className::$methodName::"
    }

    private fun resolveKey(joinPoint: ProceedingJoinPoint, keyGenerator: String, key: String): String {
        val reactiveKeyGenerator = applicationContext.getBean(keyGenerator, ReactiveKeyGenerator::class.java)
        val signature = joinPoint.signature as MethodSignature

        return reactiveKeyGenerator.generate(
            joinPoint.target,
            signature.method,
            key,
            joinPoint.args,
            signature.parameterNames
        )
    }
}