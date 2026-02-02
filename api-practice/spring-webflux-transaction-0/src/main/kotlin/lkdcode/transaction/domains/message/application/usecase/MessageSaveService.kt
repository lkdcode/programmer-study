package lkdcode.transaction.domains.message.application.usecase

import lkdcode.transaction.domains.message.domain.model.ChatMessage
import lkdcode.transaction.domains.message.domain.model.MessageMetadata
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Mono

class MessageSaveService(
    @Qualifier("r2dbcTransactionalOperator")
    private val r2dbcTransactionalOperator: TransactionalOperator,
    @Qualifier("nosqlTransactionalOperator")
    private val nosqlTransactionalOperator: TransactionalOperator,
    private val nosqlPort: NoSqlPort,
    private val rdbPort: RdbPort,
) {

    fun saveMessage(message: ChatMessage): Mono<Void> =
        r2dbcTransactionalOperator.transactional(
            nosqlPort
                .save(message)
                .then(rdbPort.save(message.toMetadata()))
                .then()
        )

fun saveMessageWithCompensation(message: ChatMessage): Mono<Void> =
    nosqlPort.save(message)
        .flatMap { messageId ->
            r2dbcTransactionalOperator
                .transactional(rdbPort.save(message.toMetadata()))
                .onErrorResume { error -> nosqlPort.deleteById(messageId).then(Mono.error(error)) }
        }
        .then()

    fun saveMessageWithOutbox(message: ChatMessage): Mono<Void> =
        r2dbcTransactionalOperator.transactional(
            rdbPort
                .save(message.toMetadata())
                .then(
                    rdbPort.saveOutbox(
                        OutboxEvent(
                            aggregateType = "ChatMessage",
                            aggregateId = message.messageId,
                            payload = message.content,
                        )
                    )
                )
                .then()
        )

    fun saveMessageWithDualOperator(message: ChatMessage): Mono<Void> =
        nosqlTransactionalOperator.transactional(
            nosqlPort.save(message)
        ).then(
            r2dbcTransactionalOperator.transactional(
                rdbPort.save(message.toMetadata())
            )
        ).then()

    private fun ChatMessage.toMetadata(): MessageMetadata =
        MessageMetadata(
            messageId = messageId,
            tagId = tag?.tagId,
            tagDescription = tag?.description,
        )
}

interface NoSqlPort {
    fun save(message: ChatMessage): Mono<String>
    fun deleteById(messageId: String): Mono<Boolean>
}

interface RdbPort {
    fun save(metadata: MessageMetadata): Mono<Long>
    fun saveOutbox(event: OutboxEvent): Mono<Long>
}

data class OutboxEvent(
    val id: Long? = null,
    val aggregateType: String,
    val aggregateId: String,
    val payload: String,
)