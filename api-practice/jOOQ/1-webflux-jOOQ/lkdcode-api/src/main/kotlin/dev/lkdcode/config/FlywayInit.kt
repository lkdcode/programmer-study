package dev.lkdcode.config


import org.flywaydb.core.Flyway
import org.springframework.boot.autoconfigure.flyway.FlywayProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(FlywayProperties::class)
class FlywayInit {

    @Bean(initMethod = "migrate")
    fun flyway(
        flywayProperties: FlywayProperties,
    ): Flyway = Flyway.configure()
        .dataSource(
            flywayProperties.url,
            flywayProperties.user,
            flywayProperties.password
        )
        .locations(*flywayProperties.locations.toTypedArray())
        .baselineOnMigrate(true)
        .load()
}