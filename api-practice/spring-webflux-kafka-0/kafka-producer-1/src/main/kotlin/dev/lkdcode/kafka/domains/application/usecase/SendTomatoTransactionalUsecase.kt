package dev.lkdcode.kafka.domains.application.usecase

import dev.lkdcode.kafka.domains.application.dto.TomatoDtoList
import dev.lkdcode.kafka.domains.domain.model.TomatoVo
import reactor.core.publisher.Mono

interface SendTomatoTransactionalUsecase {
    fun send(tomatoList: List<TomatoVo>): Mono<TomatoDtoList>
}