package dev.lkdcode.kafka.framework.kafka.consumer.rebalance

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener
import org.apache.kafka.common.TopicPartition
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class StickyRebalanceListener : ConsumerRebalanceListener {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun onPartitionsRevoked(partitions: Collection<TopicPartition>) {
        log.warn("[STICKY] *** REBALANCE START (EAGER) - revoked ALL partitions: {}", partitions)
    }

    override fun onPartitionsAssigned(partitions: Collection<TopicPartition>) {
        log.info("[STICKY] *** REBALANCE END   - assigned partitions (minimized movement): {}", partitions)
    }
}