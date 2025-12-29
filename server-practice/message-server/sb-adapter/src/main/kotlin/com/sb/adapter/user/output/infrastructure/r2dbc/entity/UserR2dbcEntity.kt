package com.sb.adapter.user.output.infrastructure.r2dbc.entity

import com.sb.domain.user.entity.User
import com.sb.domain.user.spec.UserLoginAttemptSpec
import com.sb.domain.user.value.UserRole
import com.sb.framework.r2dbc.entity.BaseEntity
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("mst_user")
class UserR2dbcEntity(
    @Id
    @Column("id")
    var id: Long? = null,

    @Column("email")
    var email: String,

    @Column("nickname")
    var nickname: String,

    @Column("password")
    var password: String?,

    @Column("sign_up_type")
    var signUpType: User.SignUpType,

    @Column("provider")
    var provider: String?,

    @Column("provider_user_id")
    var providerUserId: String?,

    @Column("last_login_at")
    var lastLoginAt: Instant? = null,

    @Column("profile_image")
    var profileImage: String? = null,

    @Column("role")
    var role: UserRole,

    @Column("login_attempt_count")
    var loginAttemptCount: Int = 0,
) : BaseEntity() {
    val isNonLocked get(): Boolean = this.loginAttemptCount > UserLoginAttemptSpec.MAX

    fun initLoginAttemptCount() {
        loginAttemptCount = 0
    }

    fun incrementLoginAttemptCount() {
        loginAttemptCount++
    }

    fun update(user: User): UserR2dbcEntity {
        this.id = user.id.value
        this.email = user.email.value
        this.nickname = user.nickname.value
        this.password = user.password?.value
        this.signUpType = user.signUpType
        this.provider = user.provider
        this.providerUserId = user.providerUserId
        this.lastLoginAt = user.lastLoginAt
        this.profileImage = user.profileImage
        this.role = user.role
        this.loginAttemptCount = user.loginAttemptCount

        return this
    }
}