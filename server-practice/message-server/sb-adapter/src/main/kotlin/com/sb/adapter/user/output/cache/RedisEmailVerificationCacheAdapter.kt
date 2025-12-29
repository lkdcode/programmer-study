package com.sb.adapter.user.output.cache

import com.sb.application.common.validator.throwIf
import com.sb.application.user.ports.output.cache.EmailVerificationPort
import com.sb.domain.user.exception.UserErrorCode
import com.sb.domain.user.spec.IdentityVerificationSpec
import com.sb.domain.user.value.IdentityVerificationToken
import com.sb.framework.cache.CacheService
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisEmailVerificationCacheAdapter(
    private val cacheService: CacheService,
) : EmailVerificationPort {

    override suspend fun save(
        signUpKey: String,
        value: String,
        ttl: Duration
    ) {
        cacheService
            .save(signUpKey, value, ttl)
            .awaitSingleOrNull()
    }

    override suspend fun validate(signUpKey: String, token: IdentityVerificationToken) {
        val value = cacheService
            .getValue(signUpKey)
            .awaitSingleOrNull()

        throwIf(
            value == null || value != token.value,
            UserErrorCode.EMAIL_VERIFICATION_TOKEN_MISMATCH
        )
    }

    override suspend fun isVerified(signUpKey: String): Boolean {
        val value = cacheService
            .getValue(signUpKey)
            .awaitSingleOrNull()

        throwIf(
            value == null || value != IdentityVerificationSpec.SIGN_UP_KEY_VERIFICATION_COMPLETED,
            UserErrorCode.EMAIL_VERIFICATION_NOT_VERIFIED
        )

        return true
    }

    override suspend fun remove(signUpKey: String) {
        cacheService
            .delete(signUpKey)
            .awaitSingleOrNull()
    }
}