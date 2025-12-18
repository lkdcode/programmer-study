package com.sb.framework.security.jwt.reactive

import com.sb.framework.security.jwt.spec.JwtSpec
import com.sb.framework.security.jwt.value.JwtProperties
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class ReactiveJwtService(
    private val reactiveJwtCreator: ReactiveJwtCreator,
    private val reactiveJwtRemover: ReactiveJwtRemover,
    private val reactiveJwtParser: ReactiveJwtParser,
    private val reactiveJwtValidator: ReactiveJwtValidator,
) {

    fun create(
        jwtProperties: JwtProperties,
        username: String,
        deviceId: String,
        userRole: String,
    ): Mono<String> = reactiveJwtCreator.create(
        jwtProperties,
        mutableMapOf(
            JwtSpec.USERNAME_KEY to username,
            JwtSpec.DEVICE_ID_KEY to deviceId,
            JwtSpec.USER_ROLE_KEY to userRole,
        )
    )

    fun getUsername(
        jwtProperties: JwtProperties,
        token: String,
    ): Mono<String> =
        reactiveJwtParser
            .removePrefix(token)
            .flatMap { parsed -> reactiveJwtParser.getUsername(jwtProperties, parsed) }

    fun getDeviceId(
        jwtProperties: JwtProperties,
        token: String,
    ): Mono<String> =
        reactiveJwtParser
            .removePrefix(token)
            .flatMap { parsed -> reactiveJwtParser.getDeviceId(jwtProperties, parsed) }

    fun remove(token: String): Mono<Void> =
        reactiveJwtParser
            .removePrefix(token)
            .flatMap { reactiveJwtRemover.remove(it) }

    fun isValid(
        jwtProperties: JwtProperties,
        token: String,
    ): Mono<Boolean> =
        reactiveJwtParser
            .removePrefix(token)
            .filter { it.isNotBlank() }
            .flatMap { reactiveJwtValidator.validate(jwtProperties, it) }
            .defaultIfEmpty(false)

    fun parsePrefix(token: String): Mono<String> =
        reactiveJwtParser.removePrefix(token)
}