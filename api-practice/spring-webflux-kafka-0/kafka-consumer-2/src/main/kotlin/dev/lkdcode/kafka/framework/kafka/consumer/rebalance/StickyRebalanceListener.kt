package dev.lkdcode.kafka.framework.kafka.consumer.rebalance

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.common.TopicPartition
import org.slf4j.LoggerFactory
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener
import org.springframework.stereotype.Component

@Component
class StickyRebalanceListener : ConsumerAwareRebalanceListener {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun onPartitionsRevokedBeforeCommit(consumer: Consumer<*, *>, partitions: Collection<TopicPartition>) {
        log.warn("[STICKY] *** REBALANCE START (EAGER) - revoked  : {}", partitions)
        log.warn("[STICKY] *** CONSUMER                - memberId : {}", consumer.groupMetadata().memberId())
    }

    override fun onPartitionsRevokedAfterCommit(consumer: Consumer<*, *>, partitions: Collection<TopicPartition>) {}

    override fun onPartitionsAssigned(consumer: Consumer<*, *>, partitions: Collection<TopicPartition>) {
        log.info("[STICKY] *** REBALANCE END           - assigned : {}", partitions)
        log.info("[STICKY] *** CONSUMER                - memberId : {}", consumer.groupMetadata().memberId())
    }
}