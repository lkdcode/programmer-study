package dev.lkdcode

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "dev.lkdcode.adapter",
        "dev.lkdcode.application",
        "dev.lkdcode.domain",
    ]
)
class PostApplication

fun main(args: Array<String>) {
    runApplication<PostApplication>(*args)
}