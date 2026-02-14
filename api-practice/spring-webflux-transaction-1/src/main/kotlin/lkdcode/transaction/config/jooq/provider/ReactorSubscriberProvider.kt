package lkdcode.transaction.config.jooq.provider

import org.jooq.SubscriberProvider
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import reactor.core.CoreSubscriber
import reactor.util.context.Context
import java.util.function.Consumer

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