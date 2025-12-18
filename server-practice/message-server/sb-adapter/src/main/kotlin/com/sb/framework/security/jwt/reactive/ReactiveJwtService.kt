package com.sb.framework.security.jwt.reactive

import com.sb.framework.security.jwt.spec.JwtSpec
import com.sb.framework.security.jwt.value.AccessTokenProperties
import com.sb.framework.security.jwt.value.RefreshTokenProperties
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class ReactiveJwtService(
    private val accessTokenProperties: AccessTokenProperties,
    private val refreshTokenProperties: RefreshTokenProperties,

    private val reactiveJwtCreator: ReactiveJwtCreator,
    private val reactiveJwtRemover: ReactiveJwtRemover,
    private val reactiveJwtParser: ReactiveJwtParser,
    private val reactiveJwtValidator: ReactiveJwtValidator,
) {

    fun createAccessToken(
        username: String,
        deviceId: String,
        userRole: String,
    ): Mono<String> = reactiveJwtCreator.create(
        accessTokenProperties,
        mutableMapOf(
            JwtSpec.USERNAME_KEY to username,
            JwtSpec.DEVICE_ID_KEY to deviceId,
            JwtSpec.USER_ROLE_KEY to userRole,
        )
    )

    fun createRefreshToken(
        username: String,
        deviceId: String,
        userRole: String,
    ): Mono<String> = reactiveJwtCreator.create(
        refreshTokenProperties,
        mutableMapOf(
            JwtSpec.USERNAME_KEY to username,
            JwtSpec.DEVICE_ID_KEY to deviceId,
            JwtSpec.USER_ROLE_KEY to userRole,
        )
    )

    fun getUsername(
        token: String,
    ): Mono<String> =
        reactiveJwtParser
            .removePrefix(token)
            .flatMap { parsed -> reactiveJwtParser.getUsername(accessTokenProperties, parsed) }

    fun remove(token: String): Mono<Void> =
        reactiveJwtParser
            .removePrefix(token)
            .flatMap { reactiveJwtRemover.remove(it) }

    fun isValidAccessToken(token: String): Mono<Boolean> =
        reactiveJwtParser
            .removePrefix(token)
            .filter { it.isNotBlank() }
            .flatMap { reactiveJwtValidator.validate(accessTokenProperties, it) }
            .defaultIfEmpty(false)

    fun parsePrefix(token: String): Mono<String> =
        reactiveJwtParser.removePrefix(token)
}