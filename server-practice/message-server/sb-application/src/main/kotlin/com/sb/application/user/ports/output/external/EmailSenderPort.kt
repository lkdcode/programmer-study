package com.sb.application.user.ports.output.external

import com.sb.domain.user.value.Email
import com.sb.domain.user.value.EmailVerificationToken

interface EmailSenderPort {
    suspend fun sendVerificationCode(email: Email, token: EmailVerificationToken)
}