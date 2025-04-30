package run.moku.framework.security.jwt

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service

@Service
class JwtService(
    private val jwtProperties: JwtProperties,
) {

    fun isAuthenticatedRequest(request: HttpServletRequest): Boolean {
        return false
    }


    fun getUsername(request: HttpServletRequest): String {
        return ""
    }
}