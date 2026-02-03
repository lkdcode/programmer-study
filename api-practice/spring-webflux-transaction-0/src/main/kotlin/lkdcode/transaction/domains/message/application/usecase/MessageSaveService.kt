package lkdcode.transaction.domains.message.application.usecase

import lkdcode.transaction.domains.message.application.port.out.NoSqlPort
import lkdcode.transaction.domains.message.application.port.out.RdbPort
import lkdcode.transaction.domains.message.domain.model.ChatMessage
import lkdcode.transaction.domains.message.domain.model.toMetadata
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Mono

@Service
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

    fun saveMessageWithDualOperator(message: ChatMessage): Mono<Void> =
        nosqlTransactionalOperator.transactional(nosqlPort.save(message))
            .then(
                r2dbcTransactionalOperator.transactional(
                    rdbPort.save(message.toMetadata())
                )
            )
            .then()

    @Transactional
    fun saveWithoutCompensation(message: ChatMessage): Mono<Void> =
        nosqlPort
            .save(message)
            .then(rdbPort.save(message.toMetadata()))
            .then()

    fun saveMessageWithCompensation(message: ChatMessage): Mono<Void> =
        nosqlPort
            .save(message)
            .flatMap { messageId ->
                r2dbcTransactionalOperator
                    .transactional(rdbPort.save(message.toMetadata()))
                    .onErrorResume { error -> nosqlPort.deleteById(messageId).then(Mono.error(error)) }
            }
            .then()

    @Transactional
    fun saveWithCompensation(message: ChatMessage): Mono<Void> =
        nosqlPort
            .save(message)
            .flatMap { messageId ->
                rdbPort
                    .save(message.toMetadata())
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

    private fun ChatMessage.toJson(): String =
        """{"messageId":"$messageId","content":"$content","senderId":"$senderId","roomId":"$roomId"}"""
}

data class OutboxEvent(
    val id: Long? = null,
    val aggregateType: String,
    val aggregateId: String,
    val payload: String,
)
