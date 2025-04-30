package run.moku.framework.security.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import run.moku.modules.users.adapter.infrastructure.jpa.query.UserQueryJpaRepository

@Component
class MokuUserDetailsService(
    private val repository: UserQueryJpaRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails = repository.findByLoginId(username)
}