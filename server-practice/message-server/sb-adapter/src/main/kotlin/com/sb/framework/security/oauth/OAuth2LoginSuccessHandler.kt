package com.sb.framework.security.oauth

import com.sb.adapter.auth.input.web.TokenIssueWebAdapter
import com.sb.application.user.ports.output.query.UserQueryPort
import com.sb.domain.user.value.Email
import com.sb.framework.mono.monoSuspend
import com.sb.framework.security.authentication.toUserAuthentication
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono


@Component
class OAuth2LoginSuccessHandler(
    private val tokenIssueWebAdapter: TokenIssueWebAdapter,
    private val userQueryPort: UserQueryPort,
    private val mappers: List<OAuth2UserInfoMapper>,
) : ServerAuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        webFilterExchange: WebFilterExchange, authentication: Authentication
    ): Mono<Void> {
        val oauthUser = authentication.principal as OAuth2User
        val provider = (authentication as OAuth2AuthenticationToken).authorizedClientRegistrationId

        val mapper = mappers.first { it.supports(provider) }
        val emailValue = mapper.getEmail(oauthUser.attributes)

        return monoSuspend {
            val authentication = userQueryPort
                .loadByEmail(Email.of(emailValue))
                .toUserAuthentication()

            tokenIssueWebAdapter.issue(webFilterExchange, authentication)
        }.then()
    }
}