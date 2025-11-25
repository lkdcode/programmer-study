package dev.lkdcode.security.jwt

import dev.lkdcode.security.jwt.spec.JwtSpec
import dev.lkdcode.security.jwt.value.JwtProperties
import org.springframework.stereotype.Service

@Service
class JwtService(
    private val creator: JwtCreator,
    private val remover: JwtRemover,
    private val parser: JwtParser,
    private val validator: JwtValidator,
) {

    fun create(
        jwtProperties: JwtProperties,
        username: String,
        userRole: String,
    ): String = creator.create(
        jwtProperties,
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
            ?.let { parser.removePrefix(it) } ?: token

        remover.remove(parsedToken)
    }

    fun isValid(
        jwtProperties: JwtProperties,
        token: String,
    ): Boolean = parser
        .removePrefix(token)
        .let { validator.validate(jwtProperties, it) }
}