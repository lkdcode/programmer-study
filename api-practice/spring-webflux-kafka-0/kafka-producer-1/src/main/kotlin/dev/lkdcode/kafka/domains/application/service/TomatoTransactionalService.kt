package dev.lkdcode.kafka.domains.application.service

import dev.lkdcode.kafka.domains.application.dto.TomatoDtoList
import dev.lkdcode.kafka.domains.application.port.output.TransactionalTomatoProducerPort
import dev.lkdcode.kafka.domains.application.usecase.SendTomatoTransactionalUsecase
import dev.lkdcode.kafka.domains.domain.model.TomatoVo
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class TomatoTransactionalService(
    private val transactionalTomatoProducerPort: TransactionalTomatoProducerPort,
) : SendTomatoTransactionalUsecase {

    override fun send(tomatoList: List<TomatoVo>): Mono<TomatoDtoList> =
        transactionalTomatoProducerPort.sendInTransaction(tomatoList)
}