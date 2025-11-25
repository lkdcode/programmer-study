package dev.lkdcode.security.authentication

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class UserAuthentication(
    val id: Long,
    val role: List<SimpleGrantedAuthority>,
    val loginId: String,
    val encodedPassword: String,
    val isNotDeleted: Boolean,
    val isNonLocked: Boolean,
) : UserDetails {

    override fun getAuthorities(): List<SimpleGrantedAuthority> = role

    override fun getPassword(): String = encodedPassword

    override fun getUsername(): String = loginId

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = isNonLocked

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = isNotDeleted
}