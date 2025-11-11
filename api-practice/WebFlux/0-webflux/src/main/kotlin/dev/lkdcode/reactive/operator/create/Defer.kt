package dev.lkdcode.reactive.operator.create

import reactor.core.publisher.Mono
import java.time.Duration
import java.time.LocalDateTime


fun main() {
    defer3()
}

fun defer1() {
    val justNow = Mono.just(LocalDateTime.now())
    val defetNow = Mono.defer { Mono.just(LocalDateTime.now()) }

    Thread.sleep(5_000)

    justNow
        .log()
        .subscribe()

    defetNow
        .log()
        .subscribe()

    Thread.sleep(5_000)

    justNow
        .log()
        .subscribe()

    defetNow
        .log()
        .subscribe()
}

fun defer2() {
    Mono
        .just("Banana")
        .delayElement(Duration.ofSeconds(5))
        .switchIfEmpty(peekaboo())
        .log()
        .subscribe(::println)

    Thread.sleep(6_000)
}

fun defer3() {
    Mono
        .just("Banana")
        .delayElement(Duration.ofSeconds(5))
        .switchIfEmpty(Mono.defer { peekaboo() })
        .log()
        .subscribe(::println)

    Thread.sleep(6_000)
}


fun peekaboo(): Mono<String> {
    println("peekaboo")
    return Mono.just("Tomato")
}