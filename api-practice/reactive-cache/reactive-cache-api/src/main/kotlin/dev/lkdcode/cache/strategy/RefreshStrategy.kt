package dev.lkdcode.cache.strategy

enum class RefreshStrategy {
    NONE,
    ON_EXPIRE,
    SOFT_TTL,
}