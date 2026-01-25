package dev.lkdcode

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReactiveCacheApplication

fun main(args: Array<String>) {
    runApplication<ReactiveCacheApplication>(*args)
}