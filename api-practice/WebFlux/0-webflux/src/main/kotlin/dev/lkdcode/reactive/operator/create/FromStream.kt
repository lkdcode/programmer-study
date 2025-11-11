package dev.lkdcode.reactive.operator.create

import reactor.core.publisher.Flux
import java.util.stream.Stream


fun main() {
    Flux
        .fromStream(
            Stream.of(
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
        .subscribe()
}