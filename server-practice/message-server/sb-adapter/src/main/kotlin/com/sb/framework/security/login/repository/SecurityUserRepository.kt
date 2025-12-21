package com.sb.framework.security.login.repository

import com.sb.application.user.ports.output.query.UserQueryPort
import com.sb.domain.user.value.Email
import com.sb.framework.security.authentication.UserAuthentication
import com.sb.framework.security.authentication.userAuthentication
import kotlinx.coroutines.reactor.mono
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

interface SecurityUserRepository {
    fun findById(loginId: String): Mono<UserAuthentication>
    fun recordLastLoginTime(userId: Long): Mono<Void>
    fun initLoginAttemptCount(userId: Long): Mono<Void>
}

@Service
class TodoSecurityUserRepository(
    private val userQueryPort: UserQueryPort,
) : SecurityUserRepository {

    override fun findById(loginId: String): Mono<UserAuthentication> =
        Mono.defer {
            mono {
                userQueryPort
                    .loadByEmail(Email.of(loginId))
                    .userAuthentication()
            }
        }

    override fun recordLastLoginTime(userId: Long): Mono<Void> {
        TODO("Not yet implemented")
    }

    override fun initLoginAttemptCount(userId: Long): Mono<Void> {
        TODO("Not yet implemented")
    }
}