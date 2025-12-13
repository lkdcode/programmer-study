package com.sb.domain.user.service

import com.sb.domain.user.aggregate.EmailVerificationAggregate
import com.sb.domain.user.entity.EmailVerification
import com.sb.domain.user.repository.EmailVerificationQueryRepository
import com.sb.domain.user.repository.EmailVerificationRepository
import com.sb.domain.user.value.Email
import com.sb.domain.user.value.EmailVerificationToken

class EmailVerificationService(
    private val repository: EmailVerificationRepository,
    private val queryRepository: EmailVerificationQueryRepository,
) {
    fun request(email: Email): EmailVerificationAggregate {
        val aggregate = EmailVerificationAggregate.create(email)
        return repository.save(aggregate)
    }

    fun verify(email: Email, token: EmailVerificationToken): EmailVerificationAggregate {
        val aggregate = queryRepository.findBy(email, token)
            ?: throw IllegalArgumentException("이메일 인증 요청을 찾을 수 없습니다.")

        aggregate.verify(token)
        return repository.save(aggregate)
    }

    fun isVerified(email: Email, token: EmailVerificationToken): Boolean =
        queryRepository.findBy(email, token)?.getVerification?.status == EmailVerification.Status.VERIFIED
}


