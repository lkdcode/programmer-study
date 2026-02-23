package dev.lkdcode.kafka.domains.application.port.output

import dev.lkdcode.kafka.domains.application.dto.TomatoDtoList
import dev.lkdcode.kafka.domains.domain.model.TomatoVo
import reactor.core.publisher.Mono

interface TransactionalTomatoProducerPort {
    fun sendInTransaction(tomatoList: List<TomatoVo>): Mono<TomatoDtoList>
}
