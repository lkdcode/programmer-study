package com.sb.framework.security.jwt.spec

object JwtSpec {
    const val TOKEN_HEADER_KEY = "Authorization"
    const val TOKEN_PREFIX = "Bearer "
    const val BLACK_LIST_KEY = "BLACK_LIST"

    const val USERNAME_KEY = "username"
    val USERNAME_TYPE = String::class.java

    const val USER_ROLE_KEY = "role"
    val USER_ROLE_TYPE = String::class.java

    const val DEVICE_ID_KEY = "deviceId"
    val DEVICE_ID_TYPE = String::class.java
}