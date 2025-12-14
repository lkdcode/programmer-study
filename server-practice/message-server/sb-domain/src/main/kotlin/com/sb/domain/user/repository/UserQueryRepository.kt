package com.sb.domain.user.repository

import com.sb.domain.user.aggregate.UserAggregate
import com.sb.domain.user.entity.User
import com.sb.domain.user.value.Email

interface UserQueryRepository {
    suspend fun findById(id: User.UserId): UserAggregate?
    suspend fun findByEmail(email: Email): UserAggregate?
    suspend fun existsByEmail(email: Email): Boolean
}