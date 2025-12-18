package com.sb.framework.security.jwt.value

import javax.crypto.SecretKey

interface JwtProperties {
    fun issuer(): String
    fun expired(): Long
    fun secretKey(): SecretKey
}