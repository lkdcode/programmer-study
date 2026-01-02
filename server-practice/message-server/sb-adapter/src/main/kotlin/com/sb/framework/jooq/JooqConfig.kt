package com.sb.framework.jooq

import io.r2dbc.spi.ConnectionFactory
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.conf.ExecuteWithoutWhere
import org.jooq.conf.RenderImplicitJoinType
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultExecuteListenerProvider
import org.jooq.tools.LoggerListener
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class JooqConfig {

    @Bean
    fun jooqDefaultConfiguration(): DefaultConfigurationCustomizer =
        DefaultConfigurationCustomizer {
            it.settings()
                .withExecuteDeleteWithoutWhere(ExecuteWithoutWhere.THROW)
                .withExecuteUpdateWithoutWhere(ExecuteWithoutWhere.THROW)

                .withRenderImplicitJoinType(RenderImplicitJoinType.THROW)
                .withRenderImplicitJoinToManyType(RenderImplicitJoinType.THROW)

                .withRenderSchema(false)
        }

    @Bean
    fun jooqDsl(cf: ConnectionFactory): DSLContext {
        val settings = Settings()
            .withRenderFormatted(true)
            .withExecuteLogging(true)

        return DSL.using(
            DefaultConfiguration()
                .set(cf)
                .set(SQLDialect.POSTGRES)
                .set(settings)
                .set(DefaultExecuteListenerProvider(LoggerListener()))
        )
    }
}