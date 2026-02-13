package lkdcode.transaction.config

import io.r2dbc.spi.Connection
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryMetadata
import org.reactivestreams.Publisher
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import reactor.core.publisher.Mono
import java.util.concurrent.atomic.AtomicInteger

class ConnectionFactorySpy(
    private val delegate: ConnectionFactory,
) : ConnectionFactory {

    private val counter = AtomicInteger(0)

    fun reset() = counter.set(0)
    fun acquireCount(): Int = counter.get()

    override fun create(): Publisher<out Connection> =
        Mono.from(delegate.create())
            .doOnNext { counter.incrementAndGet() }

    override fun getMetadata(): ConnectionFactoryMetadata = delegate.metadata
}

@TestConfiguration
class SpyConnectionFactoryConfig {

    companion object {
        @JvmStatic
        @Bean
        fun connectionFactorySpyProcessor(): BeanPostProcessor = object : BeanPostProcessor {
            override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
                if (bean is ConnectionFactory && bean !is ConnectionFactorySpy) {
                    return ConnectionFactorySpy(bean)
                }
                return bean
            }
        }
    }
}