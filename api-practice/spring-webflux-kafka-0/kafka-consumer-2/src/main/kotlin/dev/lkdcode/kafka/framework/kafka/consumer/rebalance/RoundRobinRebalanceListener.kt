package dev.lkdcode.kafka.framework.kafka.consumer.rebalance

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.common.TopicPartition
import org.slf4j.LoggerFactory
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener
import org.springframework.stereotype.Component

@Component
class RoundRobinRebalanceListener : ConsumerAwareRebalanceListener {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun onPartitionsRevokedBeforeCommit(consumer: Consumer<*, *>, partitions: Collection<TopicPartition>) {
        log.warn("[ROUND-ROBIN] *** REBALANCE START (EAGER) - revoked  : {}", partitions)
        log.warn("[ROUND-ROBIN] *** CONSUMER                - memberId : {}", consumer.groupMetadata().memberId())
    }

    override fun onPartitionsRevokedAfterCommit(consumer: Consumer<*, *>, partitions: Collection<TopicPartition>) {}

    override fun onPartitionsAssigned(consumer: Consumer<*, *>, partitions: Collection<TopicPartition>) {
        log.info("[ROUND-ROBIN] *** REBALANCE END           - assigned : {}", partitions)
        log.info("[ROUND-ROBIN] *** CONSUMER                - memberId : {}", consumer.groupMetadata().memberId())
    }
}