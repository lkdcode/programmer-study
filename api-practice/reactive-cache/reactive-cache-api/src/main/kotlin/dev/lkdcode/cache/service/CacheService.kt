package dev.lkdcode.cache.service

import reactor.core.publisher.Mono
import java.time.Duration

interface CacheService {
    fun getValue(key: String): Mono<Any>
    fun save(key: String, value: Any, ttl: Duration = Duration.ofMinutes(60L)): Mono<Void>
    fun delete(key: String): Mono<Void>
    fun deleteByPrefix(prefix: String): Mono<Long>

    fun tryLock(key: String, ttl: Duration = Duration.ofSeconds(10L)): Mono<Boolean>
    fun unlock(key: String): Mono<Void>
}