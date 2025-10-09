package demo.example.server.container

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
class MySqlContainer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        container.start()

        TestPropertyValues.of(
            "spring.datasource.url=${container.jdbcUrl}",
            "spring.datasource.username=${container.username}",
            "spring.datasource.password=${container.password}",
            "spring.datasource.driver-class-name=${container.driverClassName}"
        ).applyTo(applicationContext.environment)
    }

    companion object {
        private val container = MySQLContainer("mysql:8.0")
            .withDatabaseName("lkdcode")
            .withUsername("lkdcode")
            .withPassword("lkdcode")
    }
}