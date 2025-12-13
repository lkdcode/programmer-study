package com.sb.domain.user.repository

import com.sb.domain.user.aggregate.UserAggregate
import com.sb.domain.user.entity.User
import com.sb.domain.user.value.Email

interface UserQueryRepository {
    fun findById(id: User.UserId): UserAggregate?
    fun findByEmail(email: Email): UserAggregate?
    fun existsByEmail(email: Email): Boolean
}


