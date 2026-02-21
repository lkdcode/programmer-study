package dev.lkdcode.config

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.GenericContainer

class RedisContainerInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    override fun initialize(ctx: ConfigurableApplicationContext) {
        TestPropertyValues.of(
            "spring.data.redis.host=${container.host}",
            "spring.data.redis.port=${container.getMappedPort(REDIS_PORT)}",
            "spring.data.redis.password=$REDIS_PASSWORD",
        ).applyTo(ctx)
    }

    private class RedisContainer : GenericContainer<RedisContainer>("redis:7-alpine")

    companion object {
        private const val REDIS_PORT = 6379
        private const val REDIS_PASSWORD = "testpass"

        val container: GenericContainer<*> = RedisContainer()
            .withExposedPorts(REDIS_PORT)
            .withCommand("redis-server", "--requirepass", REDIS_PASSWORD)
            .also { it.start() }
    }
}