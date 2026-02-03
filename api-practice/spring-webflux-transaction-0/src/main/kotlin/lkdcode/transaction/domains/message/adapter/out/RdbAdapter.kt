package lkdcode.transaction.domains.message.adapter.out

import lkdcode.transaction.domains.message.application.port.out.RdbPort
import lkdcode.transaction.domains.message.application.usecase.OutboxEvent
import lkdcode.transaction.domains.message.domain.model.MessageMetadata
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import kotlin.random.Random

@Repository
class RdbAdapter(
    private val databaseClient: DatabaseClient,
) : RdbPort {

    override fun save(metadata: MessageMetadata): Mono<Long> =
        databaseClient
            .sql(
                """
            INSERT INTO message_metadata (message_id, tag_id, tag_description)
            VALUES (:messageId, :tagId, :tagDescription)
            """
            )
            .bind("messageId", metadata.messageId)
            .bindNullable("tagId", metadata.tagId, String::class.java)
            .bindNullable("tagDescription", metadata.tagDescription, String::class.java)
            .filter { statement -> statement.returnGeneratedValues("id") }
            .map { readable ->
                if (Random.nextBoolean()) {
                    throw IllegalArgumentException("--Error after Connection Acquired--")
                }

                readable.get("id", java.lang.Long::class.java)!!.toLong()
            }
            .one()
//    override fun save(metadata: MessageMetadata): Mono<Long> =
////        Mono.error(IllegalArgumentException("--Error--"))
//        databaseClient
//            .sql(
//                """
//                INSERT INTO message_metadata (message_id, tag_id, tag_description)
//                VALUES (:messageId, :tagId, :tagDescription)
//                """
//            )
//            .bind("messageId", metadata.messageId)
//            .bindNullable("tagId", metadata.tagId, String::class.java)
//            .bindNullable("tagDescription", metadata.tagDescription, String::class.java)
//            .filter { statement -> statement.returnGeneratedValues("id") }
//            .map { readable -> readable.get("id", java.lang.Long::class.java)!!.toLong() }
//            .one()

    override fun saveOutbox(event: OutboxEvent): Mono<Long> =
        databaseClient
            .sql(
                """
                INSERT INTO outbox_event (aggregate_type, aggregate_id, payload)
                VALUES (:aggregateType, :aggregateId, :payload)
                """
            )
            .bind("aggregateType", event.aggregateType)
            .bind("aggregateId", event.aggregateId)
            .bind("payload", event.payload)
            .filter { statement -> statement.returnGeneratedValues("id") }
            .map { readable -> readable.get("id", java.lang.Long::class.java)!!.toLong() }
            .one()

    private fun <T> DatabaseClient.GenericExecuteSpec.bindNullable(
        name: String,
        value: T?,
        type: Class<T>
    ): DatabaseClient.GenericExecuteSpec =
        if (value != null) bind(name, value) else bindNull(name, type)
}