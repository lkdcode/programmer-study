package lkdcode.transaction.config.jooq

import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultExecuteListenerProvider
import org.jooq.tools.LoggerListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.r2dbc.connection.R2dbcTransactionManager

@Configuration
class JooqNoBridgeConfig {

    @Bean
    fun jooqDslNoBridge(tm: R2dbcTransactionManager): DSLContext {
        val originalFactory = tm.connectionFactory!!

        val settings = Settings()
            .withRenderFormatted(true)
            .withExecuteLogging(true)

        return DSL.using(
            DefaultConfiguration()
                .set(originalFactory)
                .set(SQLDialect.POSTGRES)
                .set(settings)
                .set(DefaultExecuteListenerProvider(LoggerListener()))
        )
    }
}