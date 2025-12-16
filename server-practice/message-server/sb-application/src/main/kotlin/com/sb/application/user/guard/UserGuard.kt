package com.sb.application.user.guard

import com.sb.application.user.ports.output.query.UserQueryPort
import com.sb.domain.user.value.Email
import org.springframework.stereotype.Component

@Component
class UserGuard(
    private val userQueryPort: UserQueryPort,
) {
    suspend fun requireEmailNotRegistered(email: Email) {
        require(!userQueryPort.existsByEmail(email)) { DUPLICATE_EMAIL_MESSAGE }
    }

    companion object {
        const val DUPLICATE_EMAIL_MESSAGE = "이미 가입된 이메일입니다."
    }
}