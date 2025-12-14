package com.sb.domain.user.repository

import com.sb.domain.user.aggregate.EmailVerificationAggregate

interface EmailVerificationRepository {
    suspend fun save(aggregate: EmailVerificationAggregate): EmailVerificationAggregate
}