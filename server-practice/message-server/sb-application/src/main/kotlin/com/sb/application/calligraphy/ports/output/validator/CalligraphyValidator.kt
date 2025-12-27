package com.sb.application.calligraphy.ports.output.validator

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.user.entity.User

interface CalligraphyValidator {
    suspend fun validateOwnership(userId: User.UserId, calligraphyId: Calligraphy.CalligraphyId)
}