package com.sb.framework.security.jwt.reactive

import com.sb.framework.cache.CacheService
import com.sb.framework.security.jwt.spec.JwtSpec
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono


@Component
class ReactiveJwtRemover(
    private val cacheService: CacheService,
) {

    fun remove(token: String): Mono<Void> =
        cacheService.save(token, JwtSpec.BLACK_LIST_KEY)

    fun isRemove(token: String): Mono<Boolean> =
        cacheService
            .getValue(token)
            .map { it == JwtSpec.BLACK_LIST_KEY }
            .defaultIfEmpty(false)
}