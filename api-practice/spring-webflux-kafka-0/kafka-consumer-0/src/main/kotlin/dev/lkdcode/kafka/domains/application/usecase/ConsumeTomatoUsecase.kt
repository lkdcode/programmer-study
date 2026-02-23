package dev.lkdcode.kafka.domains.application.usecase

import reactor.core.publisher.Mono

interface ConsumeTomatoUsecase {
    fun consumeSuccess(value: String): Mono<Void>
    fun consumeSuccessBatch(values: List<String>): Mono<Void>
    fun consumeFail(value: String): Mono<Void>
    fun consumeFail(values: List<String>): Mono<Void>
}