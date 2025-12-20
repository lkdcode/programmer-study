package com.sb.framework.security.auditor

import com.sb.framework.security.authentication.UserAuthentication
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

class UserIdAuditorAware : AuditorAware<Long> {
    override fun getCurrentAuditor(): Optional<Long> {
        val auth = SecurityContextHolder.getContext().authentication
        val userId = when (val principal = auth?.principal) {
            is UserAuthentication -> principal.id
            is String -> principal.toLongOrNull()
            else -> null
        }
        return Optional.ofNullable(userId)
    }
}