package dev.lkdcode.security.jwt.policy

import dev.lkdcode.security.jwt.spec.JwtSpec
import org.springframework.cache.CacheManager
import org.springframework.stereotype.Component

@Component
class JwtRemover(
    private val cacheManager: CacheManager,
) {
    fun remove(token: String) {
        cacheManager
            .getCache(JwtSpec.BLACK_LIST_KEY)
            ?.put(token, JwtSpec.BLACK_LIST_KEY)
    }

    fun get(key: String?): String? {
        return key
            ?.let {
                cacheManager
                    .getCache(JwtSpec.BLACK_LIST_KEY)
                    ?.get(it)
                    ?.get() as? String
            }
    }
}