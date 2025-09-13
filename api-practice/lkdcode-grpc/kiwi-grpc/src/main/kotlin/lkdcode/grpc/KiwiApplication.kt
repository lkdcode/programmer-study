package lkdcode.grpc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KiwiApplication

fun main(args: Array<String>) {
    runApplication<KiwiApplication>(*args)
}