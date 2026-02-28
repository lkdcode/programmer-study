package dev.lkdcode.kafka.framework.kafka.acl

import dev.lkdcode.kafka.framework.kafka.KafkaAclConsumerGroup
import dev.lkdcode.kafka.framework.kafka.KafkaAclTopic
import dev.lkdcode.kafka.framework.kafka.KafkaAclUser
import dev.lkdcode.kafka.framework.kafka.config.SaslKafkaProps
import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.common.acl.AccessControlEntry
import org.apache.kafka.common.acl.AclBinding
import org.apache.kafka.common.acl.AclOperation
import org.apache.kafka.common.acl.AclPermissionType
import org.apache.kafka.common.errors.TopicExistsException
import org.apache.kafka.common.resource.PatternType
import org.apache.kafka.common.resource.ResourcePattern
import org.apache.kafka.common.resource.ResourceType
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.kafka.config.KafkaListenerEndpointRegistry
import org.springframework.stereotype.Component
import java.util.concurrent.ExecutionException

@Component
class KafkaAclSetup(
    private val saslKafkaProps: SaslKafkaProps,
    private val registry: KafkaListenerEndpointRegistry,
) : ApplicationRunner {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun run(args: ApplicationArguments) {
        AdminClient.create(saslKafkaProps.adminProps()).use { admin ->
            createTopics(admin)
            createAcls(admin)
        }
        startConsumers()
    }

    private fun createTopics(admin: AdminClient) {
        val topics = listOf(
            NewTopic(KafkaAclTopic.APPLE, 3, 1),
            NewTopic(KafkaAclTopic.BANANA, 3, 1),
        )
        admin.createTopics(topics).values().forEach { (topic, future) ->
            try {
                future.get()
                log.info("[ACL-SETUP] *** TOPIC CREATED  : {}", topic)
            } catch (e: ExecutionException) {
                if (e.cause is TopicExistsException) {
                    log.info("[ACL-SETUP] *** TOPIC EXISTS   : {}", topic)
                } else {
                    throw e
                }
            }
        }
    }

    private fun createAcls(admin: AdminClient) {
        val acls = listOf(
            topicAcl(KafkaAclUser.APPLE_PRODUCER, KafkaAclTopic.APPLE, AclOperation.WRITE),
            topicAcl(KafkaAclUser.APPLE_PRODUCER, KafkaAclTopic.APPLE, AclOperation.DESCRIBE),

            topicAcl(KafkaAclUser.APPLE_CONSUMER, KafkaAclTopic.APPLE, AclOperation.READ),
            topicAcl(KafkaAclUser.APPLE_CONSUMER, KafkaAclTopic.APPLE, AclOperation.DESCRIBE),
            groupAcl(KafkaAclUser.APPLE_CONSUMER, KafkaAclConsumerGroup.APPLE),

            topicAcl(KafkaAclUser.BANANA_PRODUCER, KafkaAclTopic.BANANA, AclOperation.WRITE),
            topicAcl(KafkaAclUser.BANANA_PRODUCER, KafkaAclTopic.BANANA, AclOperation.DESCRIBE),

            topicAcl(KafkaAclUser.BANANA_CONSUMER, KafkaAclTopic.BANANA, AclOperation.READ),
            topicAcl(KafkaAclUser.BANANA_CONSUMER, KafkaAclTopic.BANANA, AclOperation.DESCRIBE),
            groupAcl(KafkaAclUser.BANANA_CONSUMER, KafkaAclConsumerGroup.BANANA),
        )
        admin.createAcls(acls).all().get()
        log.info("[ACL-SETUP] *** ACL RULES CREATED : apple(producer/consumer) + banana(producer/consumer)")
    }

    private fun topicAcl(username: String, topic: String, operation: AclOperation): AclBinding {
        val resource = ResourcePattern(ResourceType.TOPIC, topic, PatternType.LITERAL)
        val entry = AccessControlEntry("User:$username", "*", operation, AclPermissionType.ALLOW)
        return AclBinding(resource, entry)
    }

    private fun groupAcl(username: String, group: String): AclBinding {
        val resource = ResourcePattern(ResourceType.GROUP, group, PatternType.LITERAL)
        val entry = AccessControlEntry("User:$username", "*", AclOperation.READ, AclPermissionType.ALLOW)
        return AclBinding(resource, entry)
    }

    private fun startConsumers() {
        registry.allListenerContainers.forEach { container ->
            if (!container.isRunning) {
                container.start()
                log.info("[ACL-SETUP] *** CONSUMER STARTED : {}", container.listenerId)
            }
        }
    }
}