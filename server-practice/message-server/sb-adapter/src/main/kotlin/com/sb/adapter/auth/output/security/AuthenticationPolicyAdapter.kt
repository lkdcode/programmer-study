package com.sb.adapter.auth.output.security

import com.sb.application.auth.ports.input.command.LoginUsecase
import com.sb.application.user.ports.output.query.UserQueryPort
import com.sb.domain.user.value.Email
import com.sb.framework.mono.monoSuspend
import com.sb.framework.security.filter.api.UserApiSecurityFilter
import com.sb.framework.security.login.policy.AuthenticationPolicy
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono


@Component
class AuthenticationPolicyAdapter(
    private val userQueryPort: UserQueryPort,
    private val loginUsecase: LoginUsecase,
) : AuthenticationPolicy {

    override fun onFailure(loginId: String): Mono<Void> =
        monoSuspend { loginUsecase.onLoginFail(Email.of(loginId)) }

    override fun isAttemptAllowed(loginId: String): Mono<Boolean> =
        monoSuspend<Boolean> {
            userQueryPort
                .loadByEmail(Email.of(loginId))
                .isAccountLocked
        }

    override fun permitAll(): Array<String> = UserApiSecurityFilter.PERMIT_ALL_URL

    override fun authenticated(): Array<String> = UserApiSecurityFilter.AUTHENTICATED_URL
}