package lkdcode.transaction.domains.message.application.port.out

import lkdcode.transaction.domains.message.domain.model.ChatMessage
import reactor.core.publisher.Mono

interface NoSqlPort {
    fun save(message: ChatMessage): Mono<String>
    fun deleteById(messageId: String): Mono<Boolean>
}