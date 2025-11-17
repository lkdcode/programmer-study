package dev.lkdcode.reactive.operator.trans

import dev.lkdcode.log.Logger
import reactor.core.publisher.Mono
import java.time.Duration

fun main() {

    Mono
        .just("hello")
        .delayElement(Duration.ofSeconds(1))
        .then(Mono.just("world1"))
        .doOnNext(Logger::onNext)
        .then(Mono.just("world2"))
        .doOnNext(Logger::onNext)
        .then(Mono.just("world3"))
        .doOnNext(Logger::onNext)
        .then(Mono.just("world4"))
        .subscribe(
            Logger::onNext,
            Logger::onError,
            Logger::onComplete,
        )

    Thread.sleep(2000)
}