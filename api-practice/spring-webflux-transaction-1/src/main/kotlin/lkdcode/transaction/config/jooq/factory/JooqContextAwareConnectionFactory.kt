package lkdcode.transaction.config.jooq.factory

import io.r2dbc.spi.Connection
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryMetadata
import io.r2dbc.spi.Wrapped
import org.reactivestreams.Publisher
import org.springframework.transaction.reactive.TransactionSynchronizationManager
import reactor.core.publisher.Mono
import reactor.util.context.Context

class JooqContextAwareConnectionFactory(
    private val delegate: ConnectionFactory
) : ConnectionFactory, Wrapped<ConnectionFactory> {

    override fun create(): Publisher<out Connection> =
        Mono.deferContextual { contextView ->
            Mono.from(delegate.create())
                .map { connection ->
                    JooqContextAwareConnection(connection, Context.of(contextView))
                }
        }

    override fun getMetadata(): ConnectionFactoryMetadata = delegate.metadata
    override fun unwrap(): ConnectionFactory = delegate
}

class JooqContextAwareConnection(
    private val delegate: Connection,
    private val context: Context
) : Connection by delegate {

    override fun close(): Publisher<Void> =
        TransactionSynchronizationManager
            .forCurrentTransaction()
            .flatMap { Mono.empty<Void>() }
            .onErrorResume { Mono.from(delegate.close()) }
            .contextWrite(context)
}