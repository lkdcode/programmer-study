package dev.lkdcode.kafka.framework.kafka.producer

import dev.lkdcode.kafka.framework.kafka.topic.KafkaTopic
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kafka.sender.KafkaSender
import reactor.kafka.sender.SenderRecord

@Component
class TomatoTransactionalProducer(
    private val sender: KafkaSender<String, String>,
) {

    private val log = LoggerFactory.getLogger(this::class.java)

    fun sendCommit(records: List<String>): Mono<Void> {
        val senderRecords = Flux.fromIterable(records).map { toSenderRecord(it) }

        return sender
            .sendTransactionally(Mono.just(senderRecords))
            .flatMap { it }
            .then()
    }

    fun sendAbort(records: List<String>): Mono<Void> {
        val tm = sender.transactionManager()
        val senderRecords = Flux.fromIterable(records).map { toSenderRecord(it) }

        return tm.begin<Void>()
            .thenMany(sender.send(senderRecords))
            .then(tm.abort<Void>())
            .doOnSuccess { _: Void? -> log.warn("[TX-ABORT] aborted intentionally") }
    }

    private fun toSenderRecord(value: String): SenderRecord<String, String, Void> =
        SenderRecord.create(ProducerRecord(KafkaTopic.TOMATO, value, value), null)
}