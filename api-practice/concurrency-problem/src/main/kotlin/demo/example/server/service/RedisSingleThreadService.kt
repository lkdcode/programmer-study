package demo.example.server.service

import demo.example.server.redis.RedisEvent.KEY
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisSingleThreadService(
    private val redis: StringRedisTemplate
) {

    fun apply(userId: Long, eventId: Long) {
        val payload = """{"userId":$userId, "eventId":$eventId}"""
        redis.opsForList().leftPush(KEY, payload)
    }
}