package com.sb.adapter.auth.input.web

import com.sb.framework.security.authentication.UserAuthentication
import com.sb.framework.security.jwt.reactive.ReactiveJwtService
import com.sb.framework.security.jwt.spec.JwtSpec
import org.springframework.http.ResponseCookie
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.time.Duration
import java.time.Instant

@Component
class TokenIssueWebAdapter(
    private val jwtService: ReactiveJwtService,
) {

    fun issue(webFilterExchange: WebFilterExchange, authentication: UserAuthentication): Mono<Void> {
        val exchange = webFilterExchange.exchange
        val username = authentication.username
        val role = authentication.authorityStringValue

        return jwtService.issueTokens(username, role)
            .doOnNext { tokens ->
                addAccessTokenHeader(exchange, tokens.accessToken)
                addRefreshTokenCookie(exchange, tokens.refreshToken, tokens.refreshTokenExpiresAt)
            }
            .then()
    }

    private fun addAccessTokenHeader(exchange: ServerWebExchange, accessToken: String) {
        exchange.response.headers.add(
            JwtSpec.TOKEN_HEADER_KEY,
            JwtSpec.TOKEN_PREFIX + accessToken
        )
    }

    private fun addRefreshTokenCookie(exchange: ServerWebExchange, refreshToken: String, expiresAt: Instant) {
        val maxAge = Duration.between(Instant.now(), expiresAt).coerceAtLeast(Duration.ZERO)
        val cookie = ResponseCookie
            .from("refresh_token", refreshToken)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(maxAge)
            .sameSite("Strict")
            .build()

        exchange.response.addCookie(cookie)
    }
}