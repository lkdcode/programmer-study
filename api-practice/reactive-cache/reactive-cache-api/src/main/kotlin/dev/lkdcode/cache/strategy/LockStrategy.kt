package dev.lkdcode.cache.strategy

enum class LockStrategy {
    NONE,
    LOCAL_LOCK,
    SPIN_LOCK,
    PUB_SUB,
}