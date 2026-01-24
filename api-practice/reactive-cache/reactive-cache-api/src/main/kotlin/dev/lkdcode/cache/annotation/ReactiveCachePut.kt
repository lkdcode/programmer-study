package dev.lkdcode.cache.annotation

/**
 * 항상 메서드를 실행하고 결과를 캐시에 저장하는 어노테이션.
 * 캐시 갱신이 필요한 경우 사용 (예: 업데이트 로직).
 *
 * @param key SpEL 표현식을 사용한 캐시 키
 * @param keyGenerator 커스텀 키 생성기 빈 이름
 * @param condition 캐시 적용 조건 SpEL 표현식
 * @param unless 캐시 저장 제외 조건 SpEL 표현식
 * @param ttl 캐시 TTL (초 단위, 기본값: 60초)
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ReactiveCachePut(
    val key: String = "",
    val keyGenerator: String = "",
    val condition: String = "",
    val unless: String = "",
    val ttl: Long = 60L,
)