package dev.lkdcode.cache.strategy

import dev.lkdcode.cache.strategy.refresh.CacheRefreshHandler
import dev.lkdcode.cache.strategy.refresh.NoneRefreshHandler
import dev.lkdcode.cache.strategy.refresh.SoftTtlRefreshHandler
import org.springframework.stereotype.Component

@Component
class RefreshStrategyResolver(
    private val noneRefreshHandler: NoneRefreshHandler,
    private val softTtlRefreshHandler: SoftTtlRefreshHandler,
) {
    fun resolve(strategy: RefreshStrategy): CacheRefreshHandler =
        when (strategy) {
            RefreshStrategy.NONE, RefreshStrategy.ON_EXPIRE -> noneRefreshHandler
            RefreshStrategy.SOFT_TTL -> softTtlRefreshHandler
        }
}
