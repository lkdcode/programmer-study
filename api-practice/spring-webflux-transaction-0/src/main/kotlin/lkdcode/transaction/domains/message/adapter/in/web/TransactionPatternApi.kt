package lkdcode.transaction.domains.message.adapter.`in`.web

import lkdcode.transaction.domains.message.adapter.`in`.web.request.MessageRequest
import lkdcode.transaction.domains.message.adapter.`in`.web.request.toDomain
import lkdcode.transaction.domains.message.application.usecase.MessageSaveService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class TransactionPatternApi(
    private val messageSaveService: MessageSaveService,
) {
    @PostMapping("/no-compensation")
    fun saveWithoutCompensation(@RequestBody request: MessageRequest): Mono<String> =
        messageSaveService.saveWithoutCompensation(request.toDomain())
            .thenReturn("완료")
            .onErrorResume { error ->
                Mono.just("RDB 실패! MongoDB에 데이터가 남아있습니다. 확인해보세요. Error: ${error.message}")
            }

    @PostMapping("/compensation")
    fun saveWithCompensation(@RequestBody request: MessageRequest): Mono<String> =
        messageSaveService.saveWithCompensation(request.toDomain())
            .thenReturn("완료")
            .onErrorResume { error ->
                Mono.just("RDB 실패 → MongoDB 롤백 완료! Error: ${error.message}")
            }

    @PostMapping("/save-message")
    fun saveMessage(@RequestBody request: MessageRequest): Mono<String> =
        messageSaveService.saveMessage(request.toDomain())
            .thenReturn("saveMessage 완료")
            .onErrorResume { Mono.just("실패: ${it.message}") }

    @PostMapping("/save-with-compensation")
    fun legacySaveWithCompensation(@RequestBody request: MessageRequest): Mono<String> =
        messageSaveService.saveMessageWithCompensation(request.toDomain())
            .thenReturn("saveMessageWithCompensation 완료")
            .onErrorResume { Mono.just("실패: ${it.message}") }

    @PostMapping("/save-with-outbox")
    fun saveWithOutboxLegacy(@RequestBody request: MessageRequest): Mono<String> =
        messageSaveService.saveMessageWithOutbox(request.toDomain())
            .thenReturn("saveMessageWithOutbox 완료")
            .onErrorResume { Mono.just("실패: ${it.message}") }

    @PostMapping("/save-with-dual-operator")
    fun saveWithDualOperator(@RequestBody request: MessageRequest): Mono<String> =
        messageSaveService.saveMessageWithDualOperator(request.toDomain())
            .thenReturn("saveMessageWithDualOperator 완료")
            .onErrorResume { Mono.just("실패: ${it.message}") }
}