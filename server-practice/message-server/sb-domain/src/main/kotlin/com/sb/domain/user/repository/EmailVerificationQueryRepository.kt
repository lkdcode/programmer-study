package com.sb.domain.user.repository

import com.sb.domain.user.aggregate.EmailVerificationAggregate
import com.sb.domain.user.value.Email
import com.sb.domain.user.value.EmailVerificationToken

interface EmailVerificationQueryRepository {
    fun findBy(email: Email, token: EmailVerificationToken): EmailVerificationAggregate?
}


