package dev.lkdcode.security.jwt.policy

import dev.lkdcode.security.jwt.spec.JwtSpec
import dev.lkdcode.security.jwt.value.JwtProperties
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class JwtCreator {

    fun create(
        jwtProperties: JwtProperties,
        claims: Map<String, *>,
    ): String {
        val now = Instant.now()

        return JwtSpec.PREFIX + Jwts.builder()
            .claims(claims)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plusSeconds(jwtProperties.expired())))
            .signWith(jwtProperties.secretKey())
            .compact()
    }
}