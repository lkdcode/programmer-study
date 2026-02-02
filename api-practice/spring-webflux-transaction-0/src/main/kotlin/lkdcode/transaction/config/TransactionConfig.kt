package lkdcode.transaction.config

import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.ReactiveMongoTransactionManager
import org.springframework.r2dbc.connection.R2dbcTransactionManager
import org.springframework.transaction.ReactiveTransactionManager
import org.springframework.transaction.reactive.TransactionalOperator

@Configuration
class TransactionConfig {

    @Bean
    @Primary
    @Qualifier("r2dbcTransactionManager")
    fun r2dbcTransactionManager(connectionFactory: ConnectionFactory): ReactiveTransactionManager =
        R2dbcTransactionManager(connectionFactory)

    @Bean
    @Primary
    @Qualifier("r2dbcTransactionalOperator")
    fun r2dbcTransactionalOperator(
        @Qualifier("r2dbcTransactionManager") transactionManager: ReactiveTransactionManager,
    ): TransactionalOperator =
        TransactionalOperator.create(transactionManager)


    @Bean
    @Qualifier("nosqlTransactionManager")
    fun nosqlTransactionManager(
        reactiveMongoDatabaseFactory: ReactiveMongoDatabaseFactory,
    ): ReactiveTransactionManager =
        ReactiveMongoTransactionManager(reactiveMongoDatabaseFactory)

    @Bean
    @Qualifier("nosqlTransactionalOperator")
    fun nosqlTransactionalOperator(
        @Qualifier("nosqlTransactionManager") transactionManager: ReactiveTransactionManager,
    ): TransactionalOperator =
        TransactionalOperator.create(transactionManager)
}
