package demo.example.server.container

import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
abstract class MySqlContainers {
    companion object {
        @Container
        @ServiceConnection
        val mySql = MySQLContainer("mysql:8.0")
    }
}