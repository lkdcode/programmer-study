package com.sb.domain.user.spec

import com.sb.domain.user.value.Email
import com.sb.domain.user.value.IdentityVerificationToken
import java.time.Duration

object IdentityVerificationSpec {
    private const val SIGN_UP_KEY = "AUTHENTICATION_EMAIL:"
    private const val PASSWORD_KEY = "AUTHENTICATION_PASSWORD:"

    const val SIGN_UP_KEY_VERIFICATION_COMPLETED = "${SIGN_UP_KEY}:VERIFICATION_COMPLETED"
    const val PASSWORD_KEY_VERIFICATION_COMPLETED = "${PASSWORD_KEY}:VERIFICATION_COMPLETED"

    fun generateToken() = IdentityVerificationToken.generate()

    fun generateSignUpKey(email: Email): String = SIGN_UP_KEY + email.value
    fun generateUpdatePasswordKey(email: Email): String = PASSWORD_KEY + email

    fun defaultTTL(): Duration = Duration.ofMinutes(10L)
}