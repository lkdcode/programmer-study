package run.moku.framework.security.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import run.moku.framework.security.jwt.JwtService

@Component
class JwtAuthenticationFilter(
    val jwtService: JwtService,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        jwtService
            .takeIf { jwtService.isAuthenticatedRequest(request) }
            ?.getUsername(request)
            ?.let {
                saveAuthentication(
                    UsernamePasswordAuthenticationToken(it, it)
                )
            }

        filterChain.doFilter(request, response)
    }

    private fun saveAuthentication(token: UsernamePasswordAuthenticationToken) {
        SecurityContextHolder.setContext(
            SecurityContextHolder.createEmptyContext()
                .apply {
                    authentication = token
                }
        )
    }
}