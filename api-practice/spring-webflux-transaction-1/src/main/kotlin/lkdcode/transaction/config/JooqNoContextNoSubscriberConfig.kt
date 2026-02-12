package lkdcode.transaction.config

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
import org.springframework.r2dbc.connection.TransactionAwareConnectionFactoryProxy

@Configuration
class JooqNoContextNoSubscriberConfig {

    @Bean
    fun jooqDslNoContextNoSubscriber(tm: R2dbcTransactionManager): DSLContext {
        val originalFactory = tm.connectionFactory!!
        val transactionAwareProxy = TransactionAwareConnectionFactoryProxy(originalFactory)

        val settings = Settings()
            .withRenderFormatted(true)
            .withExecuteLogging(true)

        return DSL.using(
            DefaultConfiguration()
                .set(transactionAwareProxy)
                .set(SQLDialect.POSTGRES)
                .set(settings)
                .set(DefaultExecuteListenerProvider(LoggerListener()))
        )
    }
}