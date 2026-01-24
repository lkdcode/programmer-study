package dev.lkdcode.cache.annotation

/**
 * Reactive 환경에서 캐시를 적용하는 어노테이션.
 * Spring의 @Cacheable과 유사하게 동작하며, Mono/Flux 반환 타입을 지원.
 *
 * @param key SpEL 표현식을 사용한 캐시 키 (예: "'user:' + #userId")
 * @param keyGenerator 커스텀 키 생성기 빈 이름 (key가 비어있을 때 사용)
 * @param condition 캐시 적용 조건 SpEL 표현식 (true일 때만 캐시)
 * @param unless 캐시 저장 제외 조건 SpEL 표현식 (#result로 결과 참조 가능)
 * @param ttl 캐시 TTL (초 단위, 기본값: 60초)
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ReactiveCacheable(
    val key: String = "",
    val keyGenerator: String = "",
    val condition: String = "",
    val unless: String = "",
    val ttl: Long = 60L,
)