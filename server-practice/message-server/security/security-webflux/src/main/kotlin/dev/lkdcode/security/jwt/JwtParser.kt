package dev.lkdcode.security.jwt

import dev.lkdcode.security.jwt.spec.JwtSpec
import dev.lkdcode.security.jwt.value.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component

@Component
class JwtParser {

    fun removePrefix(token: String?): String? = token
        ?.substringPrefix()
        ?: token

    fun getClaims(
        jwtProperties: JwtProperties,
        token: String,
    ): Claims? = try {
        Jwts
            .parser()
            .verifyWith(jwtProperties.secretKey())
            .build()
            .parseSignedClaims(token)
            .payload
    } catch (_: Exception) {
        null
    }

    fun getUsername(
        jwtProperties: JwtProperties,
        token: String,
    ): String =
        this.getClaims(jwtProperties, token)!!
            .get(JwtSpec.USERNAME_KEY, JwtSpec.USERNAME_TYPE)

    private fun String.substringPrefix(): String? =
        takeIf { it.startsWith(JwtSpec.PREFIX) }
            ?.substring(JwtSpec.PREFIX.length)
}