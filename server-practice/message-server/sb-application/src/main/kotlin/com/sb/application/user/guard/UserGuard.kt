package com.sb.application.user.guard

import com.sb.application.common.validator.throwIf
import com.sb.application.user.ports.output.query.UserQueryPort
import com.sb.domain.user.exception.UserErrorCode
import com.sb.domain.user.value.Email
import org.springframework.stereotype.Component

@Component
class UserGuard(
    private val userQueryPort: UserQueryPort,
) {

    suspend fun requireEmailNotRegistered(email: Email) =
        throwIf(userQueryPort.existsByEmail(email), UserErrorCode.DUPLICATE_EMAIL)
}