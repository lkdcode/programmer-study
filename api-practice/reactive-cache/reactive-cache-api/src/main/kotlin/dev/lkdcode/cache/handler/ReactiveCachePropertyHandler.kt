package dev.lkdcode.cache.handler

import dev.lkdcode.cache.annotation.ReactiveCacheEvict
import dev.lkdcode.cache.annotation.ReactiveCachePut
import dev.lkdcode.cache.annotation.ReactiveCacheable
import dev.lkdcode.cache.interceptor.ReactiveKeyGenerator
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class ReactiveCachePropertyHandler(
    private val applicationContext: ApplicationContext,
) {

    fun cacheProperty(
        joinPoint: ProceedingJoinPoint,
        reactiveCacheable: ReactiveCacheable
    ): Triple<String, Duration, String> {
        val cacheKey = resolveKey(joinPoint, reactiveCacheable.keyGenerator, reactiveCacheable.key)
        val ttl = Duration.ofSeconds(reactiveCacheable.ttl)
        val unless = reactiveCacheable.unless

        return Triple(cacheKey, ttl, unless)
    }

    fun cacheProperty(
        joinPoint: ProceedingJoinPoint,
        reactiveCachePut: ReactiveCachePut
    ): Triple<String, Duration, String> {
        val cacheKey = resolveKey(joinPoint, reactiveCachePut.keyGenerator, reactiveCachePut.key)
        val ttl = Duration.ofSeconds(reactiveCachePut.ttl)
        val unless = reactiveCachePut.unless

        return Triple(cacheKey, ttl, unless)
    }

    fun cacheProperty(
        joinPoint: ProceedingJoinPoint,
        reactiveCacheEvict: ReactiveCacheEvict
    ): String {
        return resolveKey(joinPoint, reactiveCacheEvict.keyGenerator, reactiveCacheEvict.key)
    }

    private fun resolveKey(joinPoint: ProceedingJoinPoint, keyGenerator: String, key: String): String {
        val keyGenerator = applicationContext.getBean(keyGenerator, ReactiveKeyGenerator::class.java)
        val signature = joinPoint.signature as MethodSignature

        return keyGenerator.generate(
            joinPoint.target,
            signature.method,
            key,
            joinPoint.args,
            signature.parameterNames
        )
    }
}