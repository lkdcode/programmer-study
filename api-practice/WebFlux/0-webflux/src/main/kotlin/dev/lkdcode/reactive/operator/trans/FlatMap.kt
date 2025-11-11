package dev.lkdcode.reactive.operator.trans

import reactor.core.publisher.Mono


fun main() {
    Mono
        .just(5)
        .flatMap {
            println(it)
            Mono.just(println("hihi"))
        }
        .subscribe()
}