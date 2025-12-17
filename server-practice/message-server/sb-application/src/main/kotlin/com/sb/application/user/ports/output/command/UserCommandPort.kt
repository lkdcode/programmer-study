package com.sb.application.user.ports.output.command

import com.sb.domain.user.entity.User
import com.sb.domain.user.model.NewUser

interface UserCommandPort {
    suspend fun save(newUser: NewUser): User.UserId
}