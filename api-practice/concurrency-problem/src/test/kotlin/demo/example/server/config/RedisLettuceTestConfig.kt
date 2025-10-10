package demo.example.server.config

import io.lettuce.core.ClientOptions
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration

@TestConfiguration
class RedisLettuceTestConfig : LettuceClientConfigurationBuilderCustomizer {

    override fun customize(builder: LettuceClientConfiguration.LettuceClientConfigurationBuilder) {
        val clientOptions = ClientOptions.builder()
            .autoReconnect(false)
            .build()
        builder.clientOptions(clientOptions)
    }
}