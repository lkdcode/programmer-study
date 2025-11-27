package dev.lkdcode.security.authentication

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono


@Component
class UserAuthenticationFailureHandler(
) : ServerAuthenticationFailureHandler {

    override fun onAuthenticationFailure(
        webFilterExchange: WebFilterExchange,
        exception: AuthenticationException,
    ): Mono<Void> =
        Mono.empty()

}