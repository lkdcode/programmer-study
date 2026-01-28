package dev.lkdcode.cache

import org.springframework.cache.CacheManager
import org.springframework.cache.concurrent.ConcurrentMapCache
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime


@Configuration
class MvcCacheConfig {

    @Bean("mvcKeyGenerator")
    fun mvcKeyGenerator(): MvcKeyGenerator = MvcKeyGenerator()

    @Bean
    fun cacheManager(): CacheManager = ConcurrentMapCacheManager("MvcCacheManager")
}

@Component
@EnableScheduling
class CacheMonitor(
    private val cacheManager: CacheManager
) {

    @Scheduled(fixedRate = 10000)
    fun printCacheStatus() {
        val manager = cacheManager as ConcurrentMapCacheManager

        println("\n========================= Cache Status [${LocalDateTime.now()}] ==============================")

        if (manager.cacheNames.isEmpty()) {
            println("(캐시 비어있음)")
        }

        manager.cacheNames.forEach { cacheName ->
            val cache = manager.getCache(cacheName) as? ConcurrentMapCache
            val nativeCache = cache?.nativeCache ?: emptyMap()

            println("📦 [$cacheName] entries: ${nativeCache.size}")
            nativeCache.forEach { (key, value) ->
                println("   └─ $key → $value")
            }
        }
        println("==================================================================================================\n")
    }
}