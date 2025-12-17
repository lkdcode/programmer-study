package com.sb.application.user.ports.output.query

import com.sb.domain.user.aggregate.UserAggregate
import com.sb.domain.user.entity.User
import com.sb.domain.user.value.Email

interface UserQueryPort {
    suspend fun loadById(id: User.UserId): UserAggregate
    suspend fun existsByEmail(email: Email): Boolean
    suspend fun loadByEmail(email: Email): UserAggregate
}