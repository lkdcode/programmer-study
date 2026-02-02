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

    @PostMapping("/save-message")
    fun saveMessage(@RequestBody request: MessageRequest): Mono<String> =
        messageSaveService
            .saveMessage(request.toDomain())
            .thenReturn("saveMessage 완료")

    @PostMapping("/save-with-compensation")
    fun saveWithCompensation(@RequestBody request: MessageRequest): Mono<String> =
        messageSaveService
            .saveMessageWithCompensation(request.toDomain())
            .thenReturn("saveMessageWithCompensation 완료")

    @PostMapping("/save-with-outbox")
    fun saveWithOutbox(@RequestBody request: MessageRequest): Mono<String> =
        messageSaveService
            .saveMessageWithOutbox(request.toDomain())
            .thenReturn("saveMessageWithOutbox 완료")

    @PostMapping("/save-with-dual-operator")
    fun saveWithDualOperator(@RequestBody request: MessageRequest): Mono<String> =
        messageSaveService
            .saveMessageWithDualOperator(request.toDomain())
            .thenReturn("saveMessageWithDualOperator 완료")
}