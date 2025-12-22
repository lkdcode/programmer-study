package com.sb.framework.security.jwt.reactive

import com.sb.framework.security.jwt.spec.JwtSpec
import com.sb.framework.security.jwt.value.JwtProperties
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.Instant
import java.util.*

@Component
class ReactiveJwtCreator {

    fun create(
        jwtProperties: JwtProperties,
        claims: Map<String, *>,
    ): Mono<String> =
        Mono.fromSupplier {
            val now = Instant.now()

            JwtSpec.TOKEN_PREFIX + Jwts.builder()
                .claims(claims)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(jwtProperties.expired())))
                .signWith(jwtProperties.secretKey())
                .compact()
        }

    fun createRefreshToken(
        jwtProperties: JwtProperties,
        claims: Map<String, *>,
    ): Mono<String> =
        Mono.fromSupplier {
            val now = Instant.now()

            Jwts.builder()
                .claims(claims)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(jwtProperties.expired())))
                .signWith(jwtProperties.secretKey())
                .compact()
        }
}