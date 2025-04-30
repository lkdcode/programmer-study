package run.moku.framework.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component
import run.moku.modules.users.adapter.infrastructure.jpa.query.UserQueryJpaRepository

@Component
class JwtLoginFilter(
    val objectMapper: ObjectMapper,
    val userQueryJpaRepository: UserQueryJpaRepository,
    val passwordEncoder: PasswordEncoder,
    authenticationManager: AuthenticationManager
) : UsernamePasswordAuthenticationFilter(authenticationManager) {

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        val body = objectMapper.readValue(request?.inputStream, Map::class.java) as Map<*, *>

        val loginId = body[LOGIN_ID_KEY] as? String ?: throw BadCredentialsException("로그인 실패")
        val password = body[PASSWORD_KEY] as? String ?: throw BadCredentialsException("로그인 실패")

        val user = userQueryJpaRepository
            .findByLoginId(loginId)
            .takeIf { passwordEncoder.matches(password, it.password) }
            ?: throw BadCredentialsException("로그인 실패")

        return UsernamePasswordAuthenticationToken(user, null)
    }

    override fun unsuccessfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        failed: AuthenticationException?
    ) {
        println("unsuccessfulAuthentication")
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        println("successfulAuthentication")
    }

    companion object {
        const val LOGIN_ID_KEY = "loginId"
        const val PASSWORD_KEY = "password"
    }
}