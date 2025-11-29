package dev.lkdcode.security.login

import dev.lkdcode.security.login.dto.UserLoginDTO
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.web.reactive.function.server.HandlerStrategies
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class UserLoginConverter(
    private val handlerStrategies: HandlerStrategies
) : ServerAuthenticationConverter {

    override fun convert(exchange: ServerWebExchange): Mono<Authentication> {
        val request = exchange.request
        if (request.method != HttpMethod.POST) return Mono.empty()

        val contentType = request.headers.contentType
        if (contentType != null && contentType != MediaType.APPLICATION_JSON) return Mono.empty()

        return ServerRequest
            .create(exchange, handlerStrategies.messageReaders())
            .bodyToMono(UserLoginDTO::class.java)
            .map {
                UsernamePasswordAuthenticationToken(it.loginId, it.password)
            }
    }
}