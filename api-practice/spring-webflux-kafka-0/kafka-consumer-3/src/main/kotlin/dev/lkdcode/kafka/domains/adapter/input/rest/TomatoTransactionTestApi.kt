package dev.lkdcode.kafka.domains.adapter.input.rest

import dev.lkdcode.kafka.framework.kafka.producer.TomatoTransactionalProducer
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class TomatoTransactionTestApi(
    private val tomatoTransactionalProducer: TomatoTransactionalProducer,
) {

    @PostMapping("/tomato/tx/commit")
    fun commit(@RequestParam count: Int): Mono<Void> =
        tomatoTransactionalProducer.sendCommit(buildRecords("commit", count))

    @PostMapping("/tomato/tx/abort")
    fun abort(@RequestParam count: Int): Mono<Void> =
        tomatoTransactionalProducer.sendAbort(buildRecords("abort", count))

    private fun buildRecords(prefix: String, count: Int): List<String> =
        (0 until count).map { i -> "${prefix}_tomato-$i" }
}