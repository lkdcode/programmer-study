package dev.lkdcode.reactive.operator

import reactor.core.publisher.Flux

fun main() {
    Flux
        .fromIterable(listOf(1, 2, 3, 4, 5, 6, 7, 8))
        .log()
        .subscribe()

    Flux
        .fromIterable(
            listOf(
                "A" to 1,
                "B" to 2,
                "C" to 3,
                "D" to 4,
                "E" to 5,
                "F" to 6,
                "G" to 7,
                "H" to 8,
            )
        )
        .log()
        .subscribe {
            println("${it.first} to ${it.second}")
        }
}