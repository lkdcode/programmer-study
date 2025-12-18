package com.sb.framework.security.login.repository

import com.sb.framework.security.authentication.UserAuthentication
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

interface SecurityUserRepository {
    fun findById(loginId: String): Mono<UserAuthentication>
    fun recordLastLoginTime(userId: Long): Mono<Void>
    fun initLoginAttemptCount(userId: Long): Mono<Void>
}

@Service
class TodoSecurityUserRepository : SecurityUserRepository{
    override fun findById(loginId: String): Mono<UserAuthentication> {
        TODO("Not yet implemented")
    }

    override fun recordLastLoginTime(userId: Long): Mono<Void> {
        TODO("Not yet implemented")
    }

    override fun initLoginAttemptCount(userId: Long): Mono<Void> {
        TODO("Not yet implemented")
    }
}