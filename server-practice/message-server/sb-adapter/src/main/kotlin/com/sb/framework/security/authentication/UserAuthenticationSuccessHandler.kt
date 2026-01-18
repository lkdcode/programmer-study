package com.sb.framework.security.authentication

import com.sb.adapter.auth.input.web.TokenIssueWebAdapter
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono


@Component
class UserAuthenticationSuccessHandler(
    private val tokenIssueWebAdapter: TokenIssueWebAdapter,
) : ServerAuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        webFilterExchange: WebFilterExchange, authentication: Authentication
    ): Mono<Void> = tokenIssueWebAdapter.issue(webFilterExchange, authentication.toUserAuthentication())
}