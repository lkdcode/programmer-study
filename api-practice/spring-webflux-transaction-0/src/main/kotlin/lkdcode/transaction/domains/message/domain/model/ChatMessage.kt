package lkdcode.transaction.domains.message.domain.model

import java.util.*

data class ChatMessage(
    val messageId: String = UUID.randomUUID().toString(),
    val content: String,
    val senderId: Long,
    val roomId: String,
    val tag: MessageTag?,
)

data class MessageTag(
    val tagId: String = UUID.randomUUID().toString(),
    val description: String,
)

data class MessageMetadata(
    val id: Long? = null,
    val messageId: String,
    val tagId: String?,
    val tagDescription: String?,
)