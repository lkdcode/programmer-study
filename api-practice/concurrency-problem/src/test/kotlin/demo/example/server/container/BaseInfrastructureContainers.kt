package demo.example.server.container

import com.redis.testcontainers.RedisContainer
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
abstract class BaseInfrastructureContainers {
    companion object {
        @Container
        @ServiceConnection
        val mySql = MySQLContainer("mysql:8.0")

        @Container
        @ServiceConnection
        val redis = RedisContainer("redis:latest")
    }
}