package com.sb.framework.security.filter.base

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component


interface AllowedOrigin {
    fun getList(): List<String>
}

@Profile("dev", "local")
@Component
class DevAllowedOrigin : AllowedOrigin {
    override fun getList(): List<String> =
        buildList {
            add("https://lkdcode.dev")
        }
}

@Profile("prod")
@Component
class ProdAllowedOrigin : AllowedOrigin {
    override fun getList(): List<String> =
        buildList {
            add("https://lkdcode.dev")
        }
}