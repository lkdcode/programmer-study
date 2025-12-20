package com.sb.application.user.ports.output.command

import com.sb.domain.user.entity.User
import com.sb.domain.user.model.NewUser
import com.sb.domain.user.value.Email

interface UserCommandPort {
    suspend fun save(newUser: NewUser): User.UserId
    suspend fun recordLoginAt(email: Email)
}