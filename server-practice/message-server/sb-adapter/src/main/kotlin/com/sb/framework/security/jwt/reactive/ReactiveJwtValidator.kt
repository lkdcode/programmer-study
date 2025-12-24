package com.sb.framework.security.jwt.reactive

import com.sb.framework.security.jwt.value.JwtProperties
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.Instant


@Component
class ReactiveJwtValidator(
    private val reactiveRemover: ReactiveJwtRemover,
    private val reactiveParser: ReactiveJwtParser,
) {

    fun validate(jwtProperties: JwtProperties, token: String): Mono<Boolean> =
        reactiveParser
            .removePrefix(token)
            .flatMap {
                Mono
                    .zip(
                        isNotRemoved(token),
                        isNotExpired(jwtProperties, token),
                        validateProperty(jwtProperties, token),
                    )
                    .map { it.t1 && it.t2 && it.t3 }
                    .defaultIfEmpty(false)
            }

    private fun isNotRemoved(token: String): Mono<Boolean> =
        reactiveRemover.isRemove(token).map { !it }

    private fun isNotExpired(jwtProperties: JwtProperties, token: String): Mono<Boolean> =
        reactiveParser
            .getClaims(jwtProperties, token)
            .map { claims ->
                claims.expiration
                    .toInstant()
                    .isAfter(Instant.now())
            }
            .defaultIfEmpty(false)

    private fun validateProperty(jwtProperties: JwtProperties, token: String): Mono<Boolean> =
        reactiveParser
            .getUsername(jwtProperties, token)
            .map { true }
            .onErrorReturn(false)
}