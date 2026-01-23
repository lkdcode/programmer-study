package dev.lkdcode.cache.annotation


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ReactiveCachePut(
    val key: String,
    val keyGenerator: String,
    val condition: String = "",
    val unless: String = "",
)