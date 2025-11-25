package dev.lkdcode.security.login.repository

import dev.lkdcode.security.authentication.UserAuthentication
import reactor.core.publisher.Mono

interface SecurityUserRepository {
    fun findById(loginId:String): Mono<UserAuthentication>
}