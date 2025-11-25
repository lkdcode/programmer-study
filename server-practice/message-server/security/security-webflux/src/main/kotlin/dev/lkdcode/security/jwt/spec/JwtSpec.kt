package dev.lkdcode.security.jwt.spec

object JwtSpec {
    const val HEADER_KEY = "Authorization"
    const val PREFIX = "Bearer "
    const val BLACK_LIST_KEY = "BLACK_LIST"

    const val USERNAME_KEY = "username"
    val USERNAME_TYPE = String::class.java

    const val USER_ROLE_KEY = "role"
    val USER_ROLE_TYPE = String::class.java
}