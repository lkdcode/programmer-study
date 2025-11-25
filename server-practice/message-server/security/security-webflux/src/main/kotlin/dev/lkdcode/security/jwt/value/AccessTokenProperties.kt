package dev.lkdcode.security.jwt.value

import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.crypto.SecretKey

@Component
class AccessTokenProperties(
    @Value("\${jwt.access-token.issuer}")
    private val issuer: String,

    @Value("\${jwt.access-token.expired}")
    private val expired: Long,

    @Value("\${jwt.access-token.secret-key}")
    private val secretKey: String,
) : JwtProperties {

    override fun issuer(): String = issuer

    override fun expired(): Long = expired

    override fun secretKey(): SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
}