package lkdcode.transaction.domains.message.application.usecase

import lkdcode.transaction.domains.message.domain.model.ChatMessage
import lkdcode.transaction.domains.message.domain.model.MessageMetadata
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Mono

class MessageSaveService(
    private val transactionalOperator: TransactionalOperator,
    private val nosqlPort: NoSqlPort,
    private val rdbPort: RdbPort,
) {

    fun saveMessage(message: ChatMessage): Mono<Void> =
        transactionalOperator.transactional(
            nosqlPort
                .save(message)
                .then(rdbPort.save(message.toMetadata()))
                .then()
        )

    fun saveMessageWithCompensation(message: ChatMessage): Mono<Void> =
        nosqlPort
            .save(message)
            .then(
                transactionalOperator.transactional(
                    rdbPort.save(message.toMetadata())
                )
            )
            .then()
            .onErrorResume { error ->
                nosqlPort
                    .deleteById(message.messageId)
                    .then(Mono.error(error))
            }

    fun saveMessageWithOutbox(message: ChatMessage): Mono<Void> =
        transactionalOperator.transactional(
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

    /**
     * [해결책 3] 두 개의 TransactionalOperator 사용
     *
     * R2DBC 용, NoSQL 용 각각의 TransactionalOperator 를 주입받아 관리할 수 있다.
     * 하지만 두 트랜잭션은 여전히 독립적이므로 완벽한 원자성을 보장하지는 않는다.
     */
    // fun saveMessageWithDualOperator(message: ChatMessage): Mono<Void> =
    //     nosqlTransactionalOperator.transactional(
    //         nosqlPort.save(message)
    //     ).then(
    //         r2dbcTransactionalOperator.transactional(
    //             rdbPort.save(message.toMetadata())
    //         )
    //     )

    private fun ChatMessage.toMetadata(): MessageMetadata =
        MessageMetadata(
            messageId = messageId,
            tagId = tag?.tagId,
            tagDescription = tag?.description,
        )
}

// 예제용 인터페이스
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