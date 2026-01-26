package dev.lkdcode.cache.annotation

/**
 * Reactive 환경에서 캐시를 적용하는 어노테이션.
 * Spring의 @Cacheable과 유사하게 동작하며, Mono/Flux 반환 타입을 지원.
 *
 * @param key 캐시 키
 * @param keyGenerator 커스텀 키 생성기 빈 이름
 * @param condition 캐시 적용 조건 SpEL 표현식
 * @param unless 캐시 저장 제외 조건 SpEL 표현식
 * @param ttl 캐시 TTL (초 단위, 기본값: 60초)
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ReactiveCacheable(
    val key: String,
    val keyGenerator: String = "simpleReactiveKeyGenerator",
    val condition: String = "",
    val unless: String = "",
    val ttl: Long = 60L,
)