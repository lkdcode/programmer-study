package com.sb.framework.security.jwt.value

import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.crypto.SecretKey

@Component
class RefreshTokenProperties(
    @Value("\${jwt.refresh-token.issuer}")
    private val issuer: String,

    @Value("\${jwt.refresh-token.expired}")
    private val expired: Long,

    @Value("\${jwt.refresh-token.secret-key}")
    private val secretKey: String,
) : JwtProperties {

    override fun issuer(): String = issuer

    override fun expired(): Long = expired

    override fun secretKey(): SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
}