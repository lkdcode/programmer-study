package dev.lkdcode.kafka.framework.kafka.consumer.rebalance

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.common.TopicPartition
import org.slf4j.LoggerFactory
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener
import org.springframework.stereotype.Component

@Component
class CooperativeStickyRebalanceListener : ConsumerAwareRebalanceListener {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun onPartitionsRevokedBeforeCommit(consumer: Consumer<*, *>, partitions: Collection<TopicPartition>) {
        log.warn("[COOPERATIVE-STICKY] *** REBALANCE START - revoked     : {} (only transferred)", partitions)
        log.warn("[COOPERATIVE-STICKY] *** CONSUMER        - memberId    : {}", consumer.groupMetadata().memberId())
    }

    override fun onPartitionsRevokedAfterCommit(consumer: Consumer<*, *>, partitions: Collection<TopicPartition>) {}

    override fun onPartitionsAssigned(consumer: Consumer<*, *>, partitions: Collection<TopicPartition>) {
        log.info("[COOPERATIVE-STICKY] *** REBALANCE END   - assigned    : {}", partitions)
        log.info("[COOPERATIVE-STICKY] *** CONSUMER        - memberId    : {}", consumer.groupMetadata().memberId())
    }

    override fun onPartitionsLost(consumer: Consumer<*, *>, partitions: Collection<TopicPartition>) {
        log.error("[COOPERATIVE-STICKY] *** PARTITIONS LOST - partitions : {}", partitions)
        log.error("[COOPERATIVE-STICKY] *** CONSUMER        - memberId   : {}", consumer.groupMetadata().memberId())
    }
}
