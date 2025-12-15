package com.sb.framework.cache

import reactor.core.publisher.Mono
import java.time.Duration

interface CacheService {
    fun getValue(key: String): Mono<Any>
    fun save(key: String, value: Any, ttl: Duration = Duration.ofMinutes(60L)): Mono<Void>
    fun delete(key: String): Mono<Void>
}