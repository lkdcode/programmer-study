package lkdcode.transaction.domains.message.adapter.out.document

import lkdcode.transaction.domains.message.domain.model.ChatMessage
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "messages")
data class MessageDocument(
    @Id
    val id: String? = null,
    val messageId: String,
    val content: String,
    val senderId: Long,
    val roomId: String,
    val createdAt: Instant = Instant.now(),
) {
    companion object {
        fun from(message: ChatMessage): MessageDocument =
            MessageDocument(
                messageId = message.messageId,
                content = message.content,
                senderId = message.senderId,
                roomId = message.roomId,
            )
    }
}