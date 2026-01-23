package dev.lkdcode.cache.annotation


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ReactiveCacheable(
    val key: String,
    val keyGenerator: String,
    val condition: String = "",
    val unless: String = "",

    val ttl: Long = 60L,
    val raceDelay: Long,
)