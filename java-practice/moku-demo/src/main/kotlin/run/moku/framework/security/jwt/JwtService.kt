package run.moku.framework.security.jwt

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service

@Service
class JwtService(
    private val properties: JwtProperties,
    private val creator: JwtCreator,
) {

    fun createToken(username: String): String = creator.create(username)

    fun validToken(token: String?) : Boolean {
        println("token: ${token}")



        return true
    }

    fun getUsername(token: String): String {
        return "lkdcode"
    }

    fun authorizationHeader(): String = JwtValues.AUTHENTICATION_HEADER
}