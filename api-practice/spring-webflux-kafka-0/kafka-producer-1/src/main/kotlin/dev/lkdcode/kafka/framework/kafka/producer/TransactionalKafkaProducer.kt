package dev.lkdcode.kafka.framework.kafka.producer

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kafka.sender.KafkaSender
import reactor.kafka.sender.SenderRecord
import reactor.kafka.sender.SenderResult

@Component
class TransactionalKafkaProducer(
    private val exactlyOnceKafkaSender: KafkaSender<String, String>,
    private val objectMapper: ObjectMapper,
) {

    fun sendInTransaction(records: List<Pair<String, Any>>, topic: String): Mono<List<SenderResult<Void>>> {
        val senderRecords = Flux
            .fromIterable(records)
            .map { it.toSenderRecord(topic) }

        return exactlyOnceKafkaSender
            .sendTransactionally(Mono.just(senderRecords))
            .flatMap { it }
            .collectList()
    }

    private fun Pair<String, Any>.toSenderRecord(topic: String): SenderRecord<String, String, Void> =
        SenderRecord.create(
            ProducerRecord<String, String>(topic, this.first, objectMapper.writeValueAsString(this.second)),
            null,
        )
}