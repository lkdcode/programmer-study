package com.sb.domain.user.aggregate

import com.sb.domain.exception.domainRequire
import com.sb.domain.exception.domainFail
import com.sb.domain.user.entity.EmailVerification
import com.sb.domain.user.exception.UserErrorCode
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
        domainRequire(verification.status == EmailVerification.Status.PENDING, UserErrorCode.EMAIL_VERIFICATION_ALREADY_PROCESSED)
        domainRequire(verification.token == token, UserErrorCode.EMAIL_VERIFICATION_TOKEN_MISMATCH)

        if (now.isAfter(verification.expiresAt)) {
            verification = verification.copy(status = EmailVerification.Status.EXPIRED)
            domainFail(UserErrorCode.EMAIL_VERIFICATION_EXPIRED)
        }

        verification = verification.copy(
            status = EmailVerification.Status.VERIFIED,
            verifiedAt = now,
        )
        return this
    }

    fun consume(now: Instant = Instant.now()): EmailVerificationAggregate {
        domainRequire(verification.status == EmailVerification.Status.VERIFIED, UserErrorCode.EMAIL_VERIFICATION_NOT_VERIFIED)
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