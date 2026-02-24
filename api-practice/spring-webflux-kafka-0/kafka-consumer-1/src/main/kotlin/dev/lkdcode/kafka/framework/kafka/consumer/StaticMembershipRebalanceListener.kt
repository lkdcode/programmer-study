package dev.lkdcode.kafka.framework.kafka.consumer

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener
import org.apache.kafka.common.TopicPartition
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class StaticMembershipRebalanceListener : ConsumerRebalanceListener {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun onPartitionsRevoked(partitions: Collection<TopicPartition>) {
        log.warn("[STATIC-MEMBERSHIP] *** REBALANCE START - revoked partitions: {}", partitions)
    }

    override fun onPartitionsAssigned(partitions: Collection<TopicPartition>) {
        log.info("[STATIC-MEMBERSHIP] *** REBALANCE END   - assigned partitions: {}", partitions)
    }
}