package com.sb.domain.calligraphy.value


data class User(
    val userId: Long,
    val username: String,
    val role: UserRole,
) {
    enum class UserRole {
        ADMIN, USER,
    }

    fun isAdmin(): Boolean = role == UserRole.ADMIN
    fun canDelete(): Boolean = role in setOf(UserRole.ADMIN)
}