package dev.lkdcode

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import reactor.blockhound.BlockHound

@SpringBootApplication
class ReactiveCacheApplication

fun main(args: Array<String>) {
    BlockHound.install()
    runApplication<ReactiveCacheApplication>(*args)
}