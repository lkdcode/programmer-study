package dev.lkdcode.reactive.operator.trans

import reactor.core.publisher.Mono


fun main() {
    Mono.just("hi")
        .map { println(it) }
        .log()
        .subscribe()
}