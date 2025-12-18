package com.sb.framework.security.jwt.reactive

import com.sb.framework.security.jwt.spec.JwtSpec
import com.sb.framework.security.jwt.value.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono


@Component
class ReactiveJwtParser {

    fun removePrefix(token: String): Mono<String> =
        Mono.justOrEmpty(token)
            .map {
                if (it.startsWith(JwtSpec.TOKEN_PREFIX))
                    it.substring(JwtSpec.TOKEN_PREFIX.length)
                else
                    it
            }

    fun getClaims(
        jwtProperties: JwtProperties,
        token: String,
    ): Mono<Claims> = Mono.fromCallable {
        Jwts.parser()
            .verifyWith(jwtProperties.secretKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }.onErrorResume {
        Mono.empty()
    }

    fun getUsername(
        jwtProperties: JwtProperties,
        token: String,
    ): Mono<String> =
        getClaims(jwtProperties, token)
            .mapNotNull { claims ->
                claims.get(JwtSpec.USERNAME_KEY, JwtSpec.USERNAME_TYPE)
            }

    fun getDeviceId(
        jwtProperties: JwtProperties,
        token: String,
    ): Mono<String> =
        getClaims(jwtProperties, token)
            .mapNotNull { claims ->
                claims.get(JwtSpec.DEVICE_ID_KEY, JwtSpec.DEVICE_ID_TYPE)
            }

    private fun String.substringPrefix(): String? =
        takeIf { it.startsWith(JwtSpec.TOKEN_PREFIX) }
            ?.substring(JwtSpec.TOKEN_PREFIX.length)
}