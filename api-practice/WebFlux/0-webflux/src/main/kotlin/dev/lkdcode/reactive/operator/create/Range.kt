package dev.lkdcode.reactive.operator.create

import reactor.core.publisher.Flux


fun main() {
    Flux
        .range(1, 6)
        .subscribe(::println)
}