package com.sb.framework.security.jwt.reactive

import com.sb.application.auth.dto.TokenPair
import com.sb.framework.security.jwt.spec.JwtSpec
import com.sb.framework.security.jwt.value.AccessTokenProperties
import com.sb.framework.security.jwt.value.RefreshTokenProperties
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Instant


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
        userRole: String,
    ): Mono<String> = reactiveJwtCreator.create(
        accessTokenProperties,
        mutableMapOf(
            JwtSpec.USERNAME_KEY to username,
            JwtSpec.USER_ROLE_KEY to userRole,
        )
    )

    fun createRefreshToken(
        username: String,
        userRole: String,
    ): Mono<String> = reactiveJwtCreator.createRefreshToken(
        refreshTokenProperties,
        mutableMapOf(
            JwtSpec.USERNAME_KEY to username,
            JwtSpec.USER_ROLE_KEY to userRole,
        )
    )

    fun issueTokens(
        username: String,
        userRole: String,
    ): Mono<TokenPair> {
        val accessMono = createAccessToken(username, userRole)
        val refreshMono = createRefreshToken(username, userRole)
        val refreshExpiredAt = Instant.now().plusMillis(refreshTokenProperties.expired())

        return Mono
            .zip(accessMono, refreshMono)
            .map { tuple ->
                TokenPair(
                    accessToken = tuple.t1,
                    refreshToken = tuple.t2,
                    refreshTokenExpiresAt = refreshExpiredAt,
                )
            }
    }

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