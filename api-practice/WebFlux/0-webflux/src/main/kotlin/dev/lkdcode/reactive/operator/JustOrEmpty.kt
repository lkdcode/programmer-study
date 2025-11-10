package dev.lkdcode.reactive.operator

import reactor.core.publisher.Mono

fun main() {
    justOrEmpty("justOrEmpty")
        .log()
        .subscribe()

    justOrEmpty(null)
        .log()
        .subscribe()
}

fun <T> justOrEmpty(t: T?): Mono<T> = Mono.justOrEmpty(t)
