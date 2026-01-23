package dev.lkdcode.cache.annotation


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ReactiveCacheEvict(
    val key: String,
    val keyGenerator: String,
    val condition: String,
)