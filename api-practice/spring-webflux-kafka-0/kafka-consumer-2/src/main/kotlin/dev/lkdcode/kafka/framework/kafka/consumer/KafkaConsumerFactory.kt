package dev.lkdcode.kafka.framework.kafka.consumer

object KafkaConsumerFactory {
    const val RANGE = "rangeAssignorKafkaListenerContainerFactory"
    const val ROUND_ROBIN = "roundRobinAssignorKafkaListenerContainerFactory"
    const val STICKY = "stickyAssignorKafkaListenerContainerFactory"
    const val COOPERATIVE_STICKY = "cooperativeStickyAssignorKafkaListenerContainerFactory"
}