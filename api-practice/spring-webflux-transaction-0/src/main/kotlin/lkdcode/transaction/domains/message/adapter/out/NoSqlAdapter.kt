package lkdcode.transaction.domains.message.adapter.out

import lkdcode.transaction.domains.message.adapter.out.document.MessageDocument
import lkdcode.transaction.domains.message.application.port.out.NoSqlPort
import lkdcode.transaction.domains.message.domain.model.ChatMessage
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class NoSqlAdapter(
    private val mongoTemplate: ReactiveMongoTemplate,
) : NoSqlPort {

    override fun save(message: ChatMessage): Mono<String> =
        mongoTemplate.save(MessageDocument.from(message))
            .map { it.messageId }

    override fun deleteById(messageId: String): Mono<Boolean> =
        mongoTemplate.remove(
            Query.query(Criteria.where("messageId").`is`(messageId)),
            MessageDocument::class.java
        ).map { it.deletedCount > 0 }
}