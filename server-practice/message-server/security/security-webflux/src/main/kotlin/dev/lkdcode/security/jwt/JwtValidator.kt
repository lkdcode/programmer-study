package dev.lkdcode.security.jwt

import dev.lkdcode.security.jwt.spec.JwtSpec
import dev.lkdcode.security.jwt.value.JwtProperties
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class JwtValidator(
    private val remover: JwtRemover,
    private val parser: JwtParser,
) {
    fun validate(jwtProperties: JwtProperties, token: String?): Boolean {
        return token != null
                && isNotRemoved(token)
                && isNotExpired(jwtProperties, token)
    }

    private fun isNotRemoved(token: String?): Boolean = remover.get(token) != JwtSpec.BLACK_LIST_KEY
    private fun isNotExpired(jwtProperties: JwtProperties, token: String): Boolean =
        parser
            .getClaims(jwtProperties, token)
            ?.expiration
            ?.toInstant()
            ?.isAfter(Instant.now()) == true
}