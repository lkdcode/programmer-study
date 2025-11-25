package dev.lkdcode.security.jwt

import dev.lkdcode.security.jwt.policy.JwtCreator
import dev.lkdcode.security.jwt.policy.JwtParser
import dev.lkdcode.security.jwt.policy.JwtRemover
import dev.lkdcode.security.jwt.policy.JwtValidator
import dev.lkdcode.security.jwt.spec.JwtSpec
import dev.lkdcode.security.jwt.value.AccessTokenProperties
import dev.lkdcode.security.jwt.value.JwtProperties
import dev.lkdcode.security.jwt.value.RefreshTokenProperties
import org.springframework.stereotype.Service

@Service
class JwtService(
    private val accessTokenProperties: AccessTokenProperties,
    private val refreshTokenProperties: RefreshTokenProperties,

    private val creator: JwtCreator,
    private val remover: JwtRemover,
    private val parser: JwtParser,
    private val validator: JwtValidator,
) {

    fun createAccessToken(
        username: String,
        userRole: String,
    ): String = creator.create(
        accessTokenProperties,
        mutableMapOf(
            JwtSpec.USERNAME_KEY to username,
            JwtSpec.USER_ROLE_KEY to userRole,
        )
    )

    fun createRefreshToken(
        username: String,
        userRole: String,
    ): String = creator.create(
        refreshTokenProperties,
        mutableMapOf(
            JwtSpec.USERNAME_KEY to username,
            JwtSpec.USER_ROLE_KEY to userRole,
        )
    )

    fun getUsername(
        jwtProperties: JwtProperties,
        token: String,
    ): String {
        val parsed = parser.removePrefix(token)
        return parser.getUsername(jwtProperties, parsed!!)
    }

    fun remove(token: String) {
        val parsedToken = token
            .takeIf { it.startsWith(JwtSpec.PREFIX) }
            ?.let { parser.removePrefix(it) }
            ?: token

        remover.remove(parsedToken)
    }

    fun validateAccessToken(
        token: String,
    ): Boolean = validateToken(token, accessTokenProperties)

    fun validateRefreshToken(
        token: String,
    ): Boolean = validateToken(token, refreshTokenProperties)

    private fun validateToken(
        token: String,
        jwtProperties: JwtProperties,
    ): Boolean = parser
        .removePrefix(token)
        .let { validator.validate(jwtProperties, it) }
}