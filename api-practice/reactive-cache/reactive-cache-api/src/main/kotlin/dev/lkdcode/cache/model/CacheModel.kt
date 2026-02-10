package dev.lkdcode.cache.model

import com.fasterxml.jackson.annotation.JsonTypeInfo

data class CacheModel(
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
    val value: Any,
    val cachedAt: Long = System.currentTimeMillis()
) {
    fun isStale(softTtlMillis: Long): Boolean =
        System.currentTimeMillis() - cachedAt > softTtlMillis
}