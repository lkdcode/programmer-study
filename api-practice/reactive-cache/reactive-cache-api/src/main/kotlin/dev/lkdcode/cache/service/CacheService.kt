package dev.lkdcode.cache.service

import dev.lkdcode.cache.model.CacheModel
import reactor.core.publisher.Mono
import java.time.Duration

interface CacheService {
    fun getValue(key: String): Mono<Any>
    fun save(key: String, value: Any, ttl: Duration = Duration.ofMinutes(60L)): Mono<Void>
    fun delete(key: String): Mono<Void>
    fun deleteByPrefix(prefix: String): Mono<Long>

    fun tryLock(key: String, ttl: Duration = Duration.ofSeconds(10L)): Mono<Boolean>
    fun unlock(key: String): Mono<Void>

    fun publishCacheReady(key: String): Mono<Long>
    fun subscribeCacheReady(key: String, timeout: Duration = Duration.ofSeconds(5L)): Mono<String>

    fun getValueWithTimestamp(key: String): Mono<CacheModel>
    fun saveWithTimestamp(key: String, value: Any, ttl: Duration): Mono<Void>
}