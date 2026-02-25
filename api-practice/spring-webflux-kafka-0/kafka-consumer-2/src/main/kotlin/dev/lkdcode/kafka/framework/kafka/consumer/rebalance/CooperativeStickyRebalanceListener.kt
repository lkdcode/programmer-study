package dev.lkdcode.kafka.framework.kafka.consumer.rebalance

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener
import org.apache.kafka.common.TopicPartition
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class CooperativeStickyRebalanceListener : ConsumerRebalanceListener {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun onPartitionsRevoked(partitions: Collection<TopicPartition>) {
        log.warn("[COOPERATIVE-STICKY] *** REBALANCE - revoked ONLY transferred partitions: {}", partitions)
    }

    override fun onPartitionsAssigned(partitions: Collection<TopicPartition>) {
        log.info("[COOPERATIVE-STICKY] *** REBALANCE - newly assigned partitions: {}", partitions)
    }

    override fun onPartitionsLost(partitions: Collection<TopicPartition>) {
        log.error("[COOPERATIVE-STICKY] *** PARTITIONS LOST (unexpected) - cannot commit offsets: {}", partitions)
    }
}