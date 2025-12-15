package com.sb.framework.redis

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.stereotype.Component
import java.time.Duration

@Configuration
@EnableAutoConfiguration(exclude = [RedisAutoConfiguration::class, RedisReactiveAutoConfiguration::class])
class ReactiveRedisConfig(
    private val property: RedisProperties
) {

    @Bean
    fun reactiveRedisConnectionFactory(): ReactiveRedisConnectionFactory {
        val standalone = RedisStandaloneConfiguration(property.host, property.port).apply {
            password = RedisPassword.of(property.password)
            database = property.database
        }

        val clientConfig = LettuceClientConfiguration.builder().apply {
            if (property.ssl) {
                useSsl()
            }
            commandTimeout(Duration.ofSeconds(3))
            shutdownTimeout(Duration.ofMillis(100))
        }.build()

        return LettuceConnectionFactory(standalone, clientConfig).also {
            it.afterPropertiesSet()
        }
    }

    @Bean
    fun redisTemplate(): ReactiveRedisOperations<String, Any> {
        val factory = reactiveRedisConnectionFactory()

        val keySerializer = StringRedisSerializer()
        val valueSerializer = Jackson2JsonRedisSerializer(Any::class.java)

        val context: RedisSerializationContext<String, Any> =
            RedisSerializationContext
                .newSerializationContext<String, Any>(keySerializer)
                .value(valueSerializer)
                .hashValue(valueSerializer)
                .hashKey(valueSerializer)
                .build()

        return ReactiveRedisTemplate(factory, context)
    }

    @Bean
    fun stringRedisTemplate(): ReactiveRedisOperations<String, String> {
        val factory = reactiveRedisConnectionFactory()

        val keySerializer = StringRedisSerializer()
        val valueSerializer = StringRedisSerializer()

        val context: RedisSerializationContext<String, String> =
            RedisSerializationContext
                .newSerializationContext<String, String>(keySerializer)
                .value(valueSerializer)
                .hashValue(valueSerializer)
                .hashKey(keySerializer)
                .build()

        return ReactiveRedisTemplate(factory, context)
    }
}

@Component
data class RedisProperties(
    @Value("\${spring.data.redis.host}")
    val host: String,

    @Value("\${spring.data.redis.port}")
    val port: Int,

    @Value("\${spring.data.redis.password}")
    val password: String,

    @Value("\${spring.data.redis.database}")
    val database: Int,

    @Value("\${spring.data.redis.ssl}")
    var ssl: Boolean,
)