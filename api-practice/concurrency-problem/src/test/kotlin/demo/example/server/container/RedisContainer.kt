package demo.example.server.container

import com.redis.testcontainers.RedisContainer
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
class RedisContainer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        container.start()
        TestPropertyValues.of(
            "spring.data.redis.host=${container.host}",
            "spring.data.redis.port=${container.firstMappedPort}",
        ).applyTo(applicationContext.environment)
    }

    companion object {

        val container = RedisContainer("redis:latest")
    }
}