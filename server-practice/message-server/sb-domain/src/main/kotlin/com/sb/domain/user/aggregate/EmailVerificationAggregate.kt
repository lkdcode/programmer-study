package com.sb.domain.user.aggregate

import com.sb.domain.user.entity.EmailVerification
import com.sb.domain.user.value.Email
import com.sb.domain.user.value.EmailVerificationToken
import java.time.Duration
import java.time.Instant

class EmailVerificationAggregate private constructor(
    private var verification: EmailVerification
) {
    val getVerification: EmailVerification get() = verification

    fun verify(
        token: EmailVerificationToken,
        now: Instant = Instant.now(),
    ): EmailVerificationAggregate {
        require(verification.status == EmailVerification.Status.PENDING) { "이미 처리된 이메일 인증입니다." }
        require(verification.token == token) { "이메일 인증 토큰이 올바르지 않습니다." }

        if (now.isAfter(verification.expiresAt)) {
            verification = verification.copy(status = EmailVerification.Status.EXPIRED)
            throw IllegalArgumentException("이메일 인증이 만료되었습니다.")
        }

        verification = verification.copy(
            status = EmailVerification.Status.VERIFIED,
            verifiedAt = now,
        )
        return this
    }

    fun consume(now: Instant = Instant.now()): EmailVerificationAggregate {
        require(verification.status == EmailVerification.Status.VERIFIED) { "이메일 인증이 완료되지 않았습니다." }
        verification = verification.copy(
            status = EmailVerification.Status.CONSUMED,
            verifiedAt = verification.verifiedAt ?: now,
        )
        return this
    }

    companion object {
        private val DEFAULT_TTL: Duration = Duration.ofMinutes(10)

        fun restore(verification: EmailVerification): EmailVerificationAggregate =
            EmailVerificationAggregate(verification)

        fun create(
            email: Email,
            token: EmailVerificationToken = EmailVerificationToken.generate(),
            now: Instant = Instant.now(),
            ttl: Duration = DEFAULT_TTL,
        ): EmailVerificationAggregate = EmailVerificationAggregate(
            EmailVerification(
                id = generateId(),
                email = email,
                token = token,
                status = EmailVerification.Status.PENDING,
                expiresAt = now.plus(ttl),
                createdAt = now,
                verifiedAt = null,
            )
        )

        private fun generateId(): EmailVerification.EmailVerificationId =
            EmailVerification.EmailVerificationId(Instant.now().toEpochMilli())
    }
}