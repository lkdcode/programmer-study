package dev.lkdcode.security.authentication

import dev.lkdcode.security.jwt.spec.JwtSpec
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@Component
class JwtBearerTokenConverter : ServerAuthenticationConverter {

    override fun convert(exchange: ServerWebExchange): Mono<Authentication> {
        val token = exchange.request.headers.getFirst(JwtSpec.HEADER_KEY) ?: return Mono.empty()

        return Mono.just(UsernamePasswordAuthenticationToken(null, token))
    }
}