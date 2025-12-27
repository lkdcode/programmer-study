package com.sb.application.calligraphy.ports.input.command

import com.sb.domain.calligraphy.entity.Calligraphy.CalligraphyId
import com.sb.domain.user.entity.User


interface DeleteCalligraphyUsecase {
    suspend fun delete(userId: User.UserId, calligraphyId: CalligraphyId)
}