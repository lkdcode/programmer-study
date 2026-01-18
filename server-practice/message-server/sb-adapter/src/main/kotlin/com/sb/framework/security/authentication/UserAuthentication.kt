package com.sb.framework.security.authentication

import com.sb.domain.calligraphy.value.Author
import com.sb.domain.user.aggregate.UserAggregate
import com.sb.domain.user.entity.User
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class UserAuthentication(
    val id: Long,
    val role: List<SimpleGrantedAuthority>,
    val loginId: String,
    val encodedPassword: String?,
    val isNotDeleted: Boolean,
    val isNonLocked: Boolean,
) : UserDetails {

    val userIdVo get() = User.UserId(id)
    val authorVo get() = Author(userIdVo)

    val authority get() = role.first()
    val authorityStringValue get() = role.first().toString()

    override fun getAuthorities(): List<SimpleGrantedAuthority> = role

    override fun getPassword(): String? = encodedPassword

    override fun getUsername(): String = loginId

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = isNonLocked

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = isNotDeleted
}

fun Authentication.toUserAuthentication(): UserAuthentication = this.principal as UserAuthentication

fun UserAggregate.toUserAuthentication(): UserAuthentication {
    val user = this.snapshot

    return UserAuthentication(
        id = user.id.value,
        role = listOf(SimpleGrantedAuthority(user.role.name)),
        loginId = user.email.value,
        encodedPassword = user.password?.value,
        isNotDeleted = false,
        isNonLocked = this.isAccountLocked,
    )
}