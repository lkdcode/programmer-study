package cool.hdd.domain.post.value

data class Author(
    val userId: Long,
    val username: String,
    val role: UserRole,
    val isLocked: Boolean,
    val isValid: Boolean,
) {

    val isNotLocked get() = !isLocked

    enum class UserRole {
        ADMIN, MODERATOR, USER, GUEST
    }

    fun isAdmin(): Boolean = role == UserRole.ADMIN
    fun canDelete(): Boolean = role in setOf(UserRole.ADMIN, UserRole.MODERATOR)
}