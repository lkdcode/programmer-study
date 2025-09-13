package lkdcode

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TomatoApplication

fun main(args: Array<String>) {
    runApplication<TomatoApplication>(*args)
}