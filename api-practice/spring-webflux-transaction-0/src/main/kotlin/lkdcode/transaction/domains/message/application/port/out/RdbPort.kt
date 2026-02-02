package lkdcode.transaction.domains.message.application.port.out

import lkdcode.transaction.domains.message.application.usecase.OutboxEvent
import lkdcode.transaction.domains.message.domain.model.MessageMetadata
import reactor.core.publisher.Mono

interface RdbPort {
    fun save(metadata: MessageMetadata): Mono<Long>
    fun saveOutbox(event: OutboxEvent): Mono<Long>
}