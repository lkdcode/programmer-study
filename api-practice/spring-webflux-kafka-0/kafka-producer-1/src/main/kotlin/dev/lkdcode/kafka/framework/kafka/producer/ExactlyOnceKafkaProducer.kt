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
class ExactlyOnceKafkaProducer(
    private val exactlyOnceKafkaSender: KafkaSender<String, String>,
    private val objectMapper: ObjectMapper,
) {

    fun sendInTransaction(records: List<Pair<String, Any>>, topic: String): Mono<List<SenderResult<Void>>> {
        val senderRecords: Flux<SenderRecord<String, String, Void>> = Flux.fromIterable(
            records.map { (key, value) ->
                SenderRecord.create(
                    ProducerRecord<String, String>(topic, key, objectMapper.writeValueAsString(value)),
                    null,
                )
            }
        )

        return exactlyOnceKafkaSender
            .sendTransactionally(Mono.just(senderRecords))
            .flatMap { it }
            .collectList()
    }
}