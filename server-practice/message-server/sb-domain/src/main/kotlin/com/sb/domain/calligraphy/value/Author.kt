package com.sb.domain.calligraphy.value

import com.sb.domain.user.entity.User


data class Author(
    val userId: User.UserId,
    val role: AuthorRole
) {

    enum class AuthorRole {
        MEMBER,
        ADMIN,
        GUEST,
    }
}