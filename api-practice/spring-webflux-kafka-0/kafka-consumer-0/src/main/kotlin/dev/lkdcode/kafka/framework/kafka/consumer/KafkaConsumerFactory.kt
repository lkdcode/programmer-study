package dev.lkdcode.kafka.framework.kafka.consumer

object KafkaConsumerFactory {
    const val REALTIME = "realtimeKafkaListenerContainerFactory"
    const val REPLAY = "replayKafkaListenerContainerFactory"
    const val BATCH = "batchKafkaListenerContainerFactory"
}