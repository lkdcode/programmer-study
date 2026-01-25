package dev.lkdcode.cache.annotation

/**
 * 캐시에서 항목을 제거하는 어노테이션.
 * 삭제 또는 데이터 무효화 시 사용.
 *
 * @param keyGenerator 커스텀 키 생성기 빈 이름
 * @param condition 캐시 제거 조건 SpEL 표현식
 * @param allEntries true일 경우 모든 캐시 항목 제거 (prefix 기반)
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ReactiveCacheEvict(
    val keyGenerator: String = "simpleKeyGenerator",
    val condition: String = "",
    val allEntries: Boolean = false,
)