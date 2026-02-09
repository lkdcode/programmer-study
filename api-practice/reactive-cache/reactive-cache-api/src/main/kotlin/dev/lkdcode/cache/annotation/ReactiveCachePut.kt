package dev.lkdcode.cache.annotation

import dev.lkdcode.cache.strategy.LockStrategy
import dev.lkdcode.cache.strategy.RefreshStrategy

/**
 * 항상 메서드를 실행하고 결과를 캐시에 저장하는 어노테이션.
 * 캐시 갱신이 필요한 경우 사용.
 *
 * @param cacheNames 캐시 이름 (키 네임스페이스). 비어있으면 ClassName::methodName 사용
 * @param key 캐시 키
 * @param keyGenerator 커스텀 키 생성기 빈 이름
 * @param condition 캐시 적용 조건 SpEL 표현식
 * @param unless 캐시 저장 제외 조건 SpEL 표현식
 * @param ttl 캐시 Hard TTL (초 단위, 기본값: 60초). Redis 만료 시간
 * @param softTtl 캐시 Soft TTL (초 단위, 기본값: -1 → ttl과 동일). softTtl 경과 시 stale 상태
 * @param lockStrategy 저장 시 락 전략 (기본값: NONE)
 * @param refreshStrategy 캐시 갱신 전략 (기본값: NONE)
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ReactiveCachePut(
    val cacheNames: Array<String> = [],
    val key: String,
    val keyGenerator: String = "simpleReactiveKeyGenerator",
    val condition: String = "",
    val unless: String = "",
    val ttl: Long = 60L,
    val softTtl: Long = -1L,
    val lockStrategy: LockStrategy = LockStrategy.NONE,
    val refreshStrategy: RefreshStrategy = RefreshStrategy.NONE,
)