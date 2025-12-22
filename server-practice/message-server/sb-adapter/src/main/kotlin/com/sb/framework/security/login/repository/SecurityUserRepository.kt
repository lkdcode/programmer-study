package com.sb.framework.security.login.repository

import com.sb.application.user.ports.output.command.UserCommandPort
import com.sb.application.user.ports.output.query.UserQueryPort
import com.sb.domain.user.entity.User
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
    private val userCommandPort: UserCommandPort,
) : SecurityUserRepository {

    override fun findById(loginId: String): Mono<UserAuthentication> =
        Mono
            .defer {
                mono {
                    userQueryPort
                        .loadByEmail(Email.of(loginId))
                        .userAuthentication()
                }
            }

    override fun recordLastLoginTime(userId: Long): Mono<Void> =
        Mono
            .defer {
                mono {
                    userCommandPort.recordLoginAt(User.UserId(userId))
                }
            }
            .then()

    override fun initLoginAttemptCount(userId: Long): Mono<Void> =
        Mono
            .defer {
                mono {
                    userCommandPort.initLoginAttemptCount(User.UserId(userId))
                }
            }
            .then()
}