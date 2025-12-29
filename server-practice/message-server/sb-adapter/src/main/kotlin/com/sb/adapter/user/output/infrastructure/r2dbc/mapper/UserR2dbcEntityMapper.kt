package com.sb.adapter.user.output.infrastructure.r2dbc.mapper

import com.sb.adapter.user.output.infrastructure.r2dbc.entity.UserR2dbcEntity
import com.sb.domain.user.aggregate.UserAggregate
import com.sb.domain.user.entity.User
import com.sb.domain.user.model.NewUser
import org.springframework.security.crypto.password.PasswordEncoder


fun UserR2dbcEntity.toAggregate(): UserAggregate =
    UserAggregate.from(
        User(
            id = userIdVo,
            email = emailVo,
            nickname = nicknameVo,
            password = passwordVo,
            signUpType = signUpTypeVo,
            provider = provider,
            providerUserId = providerUserId,
            loginAttemptCount = loginAttemptCount,
            createdAt = createdAt,
            updatedAt = updatedAt,
            role = role,
            profileImage = profileImage,
            lastLoginAt = lastLoginAt,
        )
    )

fun NewUser.toEntity(passwordEncoder: PasswordEncoder): UserR2dbcEntity =
    UserR2dbcEntity(
        email = this.email.value,
        nickname = this.nickname.value,
        password = this.password?.let { passwordEncoder.encode(it.value) },
        signUpType = this.signUpType,
        provider = this.provider,
        providerUserId = this.providerUserId,
        profileImage = this.profileImage,
        role = this.role,
    )

fun User.toEntity(): UserR2dbcEntity =
    UserR2dbcEntity(
        email = this.email.value,
        nickname = this.nickname.value,
        password = this.password?.value,
        signUpType = this.signUpType,
        provider = this.provider,
        providerUserId = this.providerUserId,
        profileImage = this.profileImage,
        role = this.role,
    )