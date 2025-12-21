package com.sb.framework.security.authentication

import com.sb.domain.user.aggregate.UserAggregate
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

fun Authentication.userAuthentication(): UserAuthentication = this.principal as UserAuthentication

fun UserAggregate.userAuthentication(): UserAuthentication = UserAuthentication(
    id = this.idValue,
    role = listOf(SimpleGrantedAuthority(this.role.name)),
    loginId = this.emailValue,
    encodedPassword = this.passwordValue,
    isNotDeleted = false,
    isNonLocked = false,
)