package dev.lkdcode.kafka.domains.application.service

import dev.lkdcode.kafka.domains.application.usecase.ConsumeTomatoUsecase
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class ConsumeTomatoService : ConsumeTomatoUsecase {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun consumeSuccess(value: String): Mono<Void> {
        log.info("[SUCCESS] consumed: {}", value)
        return Mono.empty()
    }

    override fun consumeSuccessBatch(values: List<String>): Mono<Void> {
        log.info("[SUCCESS-BATCH] consumed {} records", values.size)
        values.forEach { log.info("[SUCCESS-BATCH] {}", it) }
        return Mono.empty()
    }

    override fun consumeFail(value: String): Mono<Void> =
        Mono.delay(Duration.ofSeconds(5))
            .then(Mono.error(RuntimeException("consume failed: $value")))
}
