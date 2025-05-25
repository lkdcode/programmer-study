package run.moku.modules.gomoku.application.value

import java.util.*

@JvmInline
value class RoomId(
    val value: String
) {
    companion object {
        fun init(): RoomId {
            return RoomId(UUID.randomUUID().toString())
        }
    }
}