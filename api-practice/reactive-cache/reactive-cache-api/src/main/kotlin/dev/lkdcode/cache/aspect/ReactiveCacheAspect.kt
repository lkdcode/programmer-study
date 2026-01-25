//package dev.lkdcode.cache.aspect
//
//import dev.lkdcode.cache.annotation.ReactiveCacheEvict
//import dev.lkdcode.cache.annotation.ReactiveCachePut
//import dev.lkdcode.cache.annotation.ReactiveCacheable
//import dev.lkdcode.cache.handler.ReactiveCacheConditionHandler
//import dev.lkdcode.cache.interceptor.ReactiveKeyGenerator
//import dev.lkdcode.cache.service.CacheService
//import org.aspectj.lang.ProceedingJoinPoint
//import org.aspectj.lang.annotation.Around
//import org.aspectj.lang.annotation.Aspect
//import org.aspectj.lang.reflect.MethodSignature
//import org.springframework.context.ApplicationContext
//import org.springframework.context.expression.MethodBasedEvaluationContext
//import org.springframework.core.DefaultParameterNameDiscoverer
//import org.springframework.expression.spel.standard.SpelExpressionParser
//import org.springframework.stereotype.Component
//import reactor.core.publisher.Flux
//import reactor.core.publisher.Mono
//import java.time.Duration
//
//@Aspect
//@Component
//class ReactiveCacheAspect(
//    private val cacheService: CacheService,
//    private val conditionHandler: ReactiveCacheConditionHandler,
//    private val applicationContext: ApplicationContext,
//) {
//    private val parser = SpelExpressionParser()
//    private val parameterNameDiscoverer = DefaultParameterNameDiscoverer()
//
//    @Around("@annotation(reactiveCacheable)")
//    fun handleCacheable(joinPoint: ProceedingJoinPoint, reactiveCacheable: ReactiveCacheable): Any {
//        if (conditionHandler.shouldNotCache(joinPoint, reactiveCacheable.condition)) return joinPoint.proceed()
//        val (cacheKey, ttl, unless) = cacheableProperty(joinPoint, reactiveCacheable)
//
//        return when (val result = joinPoint.proceed()) {
//            is Mono<*> -> handleMonoCacheable(result, cacheKey, ttl, unless, joinPoint)
//            is Flux<*> -> handleFluxCacheable(result, cacheKey, ttl, unless, joinPoint)
//            else -> result
//        }
//    }
//
//    private fun cacheableProperty(
//        joinPoint: ProceedingJoinPoint,
//        reactiveCacheable: ReactiveCacheable
//    ): Triple<String, Duration, String> {
//        val cacheKey = resolveKey(joinPoint, reactiveCacheable.key, reactiveCacheable.keyGenerator)
//        val ttl = Duration.ofSeconds(reactiveCacheable.ttl)
//        val unless = reactiveCacheable.unless
//
//        return Triple(cacheKey, ttl, unless)
//    }
//
//    @Around("@annotation(reactiveCachePut)")
//    fun handleCachePut(joinPoint: ProceedingJoinPoint, reactiveCachePut: ReactiveCachePut): Any {
//        if (conditionHandler.shouldNotCache(joinPoint, reactiveCachePut.condition)) return joinPoint.proceed()
//
//        val cacheKey = resolveKey(joinPoint, reactiveCachePut.key, reactiveCachePut.keyGenerator)
//        val ttl = Duration.ofSeconds(reactiveCachePut.ttl)
//        val unless = reactiveCachePut.unless
//
//        return when (val result = joinPoint.proceed()) {
//            is Mono<*> -> handleMonoCachePut(result, cacheKey, ttl, unless, joinPoint)
//            is Flux<*> -> handleFluxCachePut(result, cacheKey, ttl, unless, joinPoint)
//            else -> result
//        }
//    }
//
//    @Around("@annotation(reactiveCacheEvict)")
//    fun handleCacheEvict(joinPoint: ProceedingJoinPoint, reactiveCacheEvict: ReactiveCacheEvict): Any {
//        val condition = reactiveCacheEvict.condition
//        if (!conditionHandler.shouldCache(joinPoint, condition)) {
//            return joinPoint.proceed()
//        }
//
//        val cacheKey = resolveKey(joinPoint, reactiveCacheEvict.key, reactiveCacheEvict.keyGenerator)
//
//        return when (val result = joinPoint.proceed()) {
//            is Mono<*> -> result.flatMap { cacheService.delete(cacheKey).thenReturn(it) }
//            is Flux<*> -> result.collectList()
//                .flatMap { list -> cacheService.delete(cacheKey).thenReturn(list) }
//                .flatMapMany { Flux.fromIterable(it) }
//
//            else -> result
//        }
//    }
//
//    private fun handleMonoCacheable(
//        result: Mono<*>,
//        cacheKey: String,
//        ttl: Duration,
//        unless: String,
//        joinPoint: ProceedingJoinPoint
//    ): Mono<*> {
//        return cacheService.getValue(cacheKey)
//            .flatMap { cached -> Mono.just(cached) }
//            .switchIfEmpty(
//                result.flatMap { value ->
//                    if (shouldCacheResult(value, unless, joinPoint)) {
//                        cacheService.save(cacheKey, value as Any, ttl).thenReturn(value)
//                    } else {
//                        Mono.just(value)
//                    }
//                }
//            )
//    }
//
//    private fun handleFluxCacheable(
//        result: Flux<*>,
//        cacheKey: String,
//        ttl: Duration,
//        unless: String,
//        joinPoint: ProceedingJoinPoint
//    ): Flux<*> {
//        return cacheService.getValue(cacheKey)
//            .flatMapMany { cached ->
//                when (cached) {
//                    is List<*> -> Flux.fromIterable(cached)
//                    else -> Flux.just(cached)
//                }
//            }
//            .switchIfEmpty(
//                result.collectList()
//                    .flatMap { list ->
//                        if (shouldCacheResult(list, unless, joinPoint)) {
//                            cacheService.save(cacheKey, list as Any, ttl).thenReturn(list)
//                        } else {
//                            Mono.just(list)
//                        }
//                    }
//                    .flatMapMany { Flux.fromIterable(it) }
//            )
//    }
//
//    private fun handleMonoCachePut(
//        result: Mono<*>,
//        cacheKey: String,
//        ttl: Duration,
//        unless: String,
//        joinPoint: ProceedingJoinPoint
//    ): Mono<*> {
//        return result.flatMap { value ->
//            if (shouldCacheResult(value, unless, joinPoint)) {
//                cacheService.save(cacheKey, value as Any, ttl).thenReturn(value)
//            } else {
//                Mono.just(value)
//            }
//        }
//    }
//
//    private fun handleFluxCachePut(
//        result: Flux<*>,
//        cacheKey: String,
//        ttl: Duration,
//        unless: String,
//        joinPoint: ProceedingJoinPoint
//    ): Flux<*> {
//        return result
//            .collectList()
//            .flatMap { list ->
//                if (shouldCacheResult(list, unless, joinPoint)) {
//                    cacheService.save(cacheKey, list as Any, ttl).thenReturn(list)
//                } else {
//                    Mono.just(list)
//                }
//            }
//            .flatMapMany { Flux.fromIterable(it) }
//    }
//
//    private fun resolveKey(joinPoint: ProceedingJoinPoint, key: String, keyGeneratorName: String): String {
//        return if (key.isNotBlank()) {
//            resolveSpelKey(joinPoint, key)
//        } else if (keyGeneratorName.isNotBlank()) {
//            val keyGenerator = applicationContext.getBean(keyGeneratorName, ReactiveKeyGenerator::class.java)
//            val signature = joinPoint.signature as MethodSignature
//            keyGenerator.generate(joinPoint.target, signature.method, *joinPoint.args)
//        } else {
//            generateDefaultKey(joinPoint)
//        }
//    }
//
//    private fun resolveSpelKey(joinPoint: ProceedingJoinPoint, key: String): String {
//        val signature = joinPoint.signature as MethodSignature
//        val context = MethodBasedEvaluationContext(
//            joinPoint.target,
//            signature.method,
//            joinPoint.args,
//            parameterNameDiscoverer
//        )
//
//        return parser.parseExpression(key).getValue(context, String::class.java)
//            ?: generateDefaultKey(joinPoint)
//    }
//
//    private fun generateDefaultKey(joinPoint: ProceedingJoinPoint): String {
//        val signature = joinPoint.signature as MethodSignature
//        val className = joinPoint.target.javaClass.simpleName
//        val methodName = signature.method.name
//        val args = joinPoint.args.joinToString(",") { it?.toString() ?: "null" }
//
//        return "$className:$methodName:$args"
//    }
//
//    private fun shouldCacheResult(result: Any?, unless: String, joinPoint: ProceedingJoinPoint): Boolean {
//        if (unless.isBlank()) return true
//        if (result == null) return false
//
//        val signature = joinPoint.signature as MethodSignature
//        val context = MethodBasedEvaluationContext(
//            joinPoint.target,
//            signature.method,
//            joinPoint.args,
//            parameterNameDiscoverer
//        )
//        context.setVariable("result", result)
//
//        return parser.parseExpression(unless).getValue(context, Boolean::class.java) != true
//    }
//}
