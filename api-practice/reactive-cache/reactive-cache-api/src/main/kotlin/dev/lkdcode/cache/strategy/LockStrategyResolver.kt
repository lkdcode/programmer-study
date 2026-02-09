package dev.lkdcode.cache.strategy

import dev.lkdcode.cache.strategy.lock.CacheLockHandler
import dev.lkdcode.cache.strategy.lock.NoneLockHandler
import dev.lkdcode.cache.strategy.lock.PubSubLockHandler
import dev.lkdcode.cache.strategy.lock.SpinLockHandler
import org.springframework.stereotype.Component

@Component
class LockStrategyResolver(
    private val noneLockHandler: NoneLockHandler,
    private val spinLockHandler: SpinLockHandler,
    private val pubSubLockHandler: PubSubLockHandler,
) {
    fun resolve(strategy: LockStrategy): CacheLockHandler =
        when (strategy) {
            LockStrategy.NONE, LockStrategy.LOCAL_LOCK -> noneLockHandler
            LockStrategy.SPIN_LOCK -> spinLockHandler
            LockStrategy.PUB_SUB -> pubSubLockHandler
        }
}
