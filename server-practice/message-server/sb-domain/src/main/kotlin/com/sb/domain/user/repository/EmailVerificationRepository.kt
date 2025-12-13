package com.sb.domain.user.repository

import com.sb.domain.user.aggregate.EmailVerificationAggregate

interface EmailVerificationRepository {
    fun save(aggregate: EmailVerificationAggregate): EmailVerificationAggregate
}


