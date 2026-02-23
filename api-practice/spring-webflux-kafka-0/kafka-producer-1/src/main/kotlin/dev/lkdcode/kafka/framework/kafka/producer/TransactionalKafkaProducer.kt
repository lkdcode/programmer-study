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

    fun <T> sendInTransaction(
        records: List<T>,
        topic: String,
        keySelector: (T) -> String
    ): Mono<List<SenderResult<Void>>> {
        val senderRecords = Flux
            .fromIterable(records)
            .map { payload ->
                val key = keySelector(payload)

                SenderRecord.create<String, String, Void>(
                    ProducerRecord(topic, key, objectMapper.writeValueAsString(payload)), null
                )
            }

        return exactlyOnceKafkaSender
            .sendTransactionally(Mono.just(senderRecords))
            .flatMap { it }
            .collectList()
    }
}