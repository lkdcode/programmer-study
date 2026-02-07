package dev.lkdcode.config

import io.r2dbc.spi.Connection
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryMetadata
import io.r2dbc.spi.Wrapped
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.SubscriberProvider
import org.jooq.conf.ExecuteWithoutWhere
import org.jooq.conf.RenderImplicitJoinType
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultExecuteListenerProvider
import org.jooq.tools.LoggerListener
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.r2dbc.connection.R2dbcTransactionManager
import org.springframework.r2dbc.connection.TransactionAwareConnectionFactoryProxy
import org.springframework.transaction.reactive.TransactionSynchronizationManager
import reactor.core.CoreSubscriber
import reactor.core.publisher.Mono
import reactor.util.context.Context
import java.util.function.Consumer

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
    fun jooqDsl(
        tm: R2dbcTransactionManager
    ): DSLContext {
        val originalFactory = tm.connectionFactory!!
        val transactionAwareProxy = TransactionAwareConnectionFactoryProxy(originalFactory)
        val jooqWrapperFactory = JooqContextAwareConnectionFactory(transactionAwareProxy)

        val settings = Settings()
            .withRenderFormatted(true)
            .withExecuteLogging(true)

        return DSL.using(
            DefaultConfiguration()
                .set(transactionAwareProxy)
//                .set(jooqWrapperFactory)
                .set(SQLDialect.POSTGRES)
                .set(settings)
                .set(DefaultExecuteListenerProvider(LoggerListener()))
//                .set(ReactorSubscriberProvider())
        )
    }
}

class ReactorSubscriberProvider : SubscriberProvider<Context> {

    override fun context(): Context = Context.empty()

    override fun context(subscriber: Subscriber<*>): Context =
        if (subscriber is CoreSubscriber<*>) {
            subscriber.currentContext()
        } else {
            Context.empty()
        }

    override fun <T : Any> subscriber(
        onSubscribe: Consumer<in Subscription>,
        onNext: Consumer<in T>,
        onError: Consumer<in Throwable>,
        onComplete: Runnable,
        context: Context
    ): Subscriber<T> {
        return object : CoreSubscriber<T> {
            override fun currentContext(): Context = context
            override fun onSubscribe(s: Subscription) = onSubscribe.accept(s)
            override fun onNext(t: T) = onNext.accept(t)
            override fun onError(t: Throwable) = onError.accept(t)
            override fun onComplete() = onComplete.run()
        }
    }
}

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