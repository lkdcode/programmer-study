package lkdcode.transaction.domains.message.adapter.`in`.web.request

import lkdcode.transaction.domains.message.domain.model.ChatMessage
import lkdcode.transaction.domains.message.domain.model.MessageTag

data class MessageRequest(
    val content: String,
    val senderId: Long,
    val roomId: String,
    val tagDescription: String? = null,
)

fun MessageRequest.toDomain() = ChatMessage(
    content = content,
    senderId = senderId,
    roomId = roomId,
    tag = tagDescription?.let { MessageTag(description = it) },
)