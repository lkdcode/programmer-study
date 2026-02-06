package dev.lkdcode.config

import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.r2dbc.connection.R2dbcTransactionManager
import org.springframework.transaction.reactive.TransactionalOperator

@Configuration
class TransactionConfig {

    @Bean("transactionManager")
    @Primary
    fun r2dbcTransactionManager(connectionFactory: ConnectionFactory): R2dbcTransactionManager =
        R2dbcTransactionManager(connectionFactory)

    @Bean("r2dbcTransactionalOperator")
    @Primary
    fun r2dbcTransactionalOperator(
        @Qualifier("transactionManager") tm: R2dbcTransactionManager
    ): TransactionalOperator = TransactionalOperator.create(tm)
}
