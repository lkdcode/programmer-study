package dev.lkdcode.kafka.framework.kafka.consumer.rebalance

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener
import org.apache.kafka.common.TopicPartition
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class RoundRobinRebalanceListener : ConsumerRebalanceListener {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun onPartitionsRevoked(partitions: Collection<TopicPartition>) {
        log.warn("[ROUND-ROBIN] *** REBALANCE START (EAGER) - revoked ALL partitions: {}", partitions)
    }

    override fun onPartitionsAssigned(partitions: Collection<TopicPartition>) {
        log.info("[ROUND-ROBIN] *** REBALANCE END   - assigned partitions: {}", partitions)
    }
}