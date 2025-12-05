package cool.hdd.domain.user.entity

import cool.hdd.domain.user.value.Password
import cool.hdd.domain.user.value.UserRole
import java.time.Instant

data class User(
    val id: UserId,

    val email: String,
    val nickname: String,
    val password: Password,
    val profileImage: String,

    val lastLoginAt: Instant,
    val role: UserRole,
) {

    @JvmInline
    value class UserId(val value: Long)

    fun updateNickname(newNickname: String): User = this.copy(nickname = newNickname)
    fun updatePassword(newPassword: Password): User = this.copy(password = newPassword)
    fun updateProfileImage(newProfileImage: String): User = this.copy(profileImage = newProfileImage)
}