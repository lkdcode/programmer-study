package dev.lkdcode.kafka.framework.kafka.config

import dev.lkdcode.kafka.framework.kafka.KafkaAclUser
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.config.SaslConfigs
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class SaslKafkaProps(
    @Value("\${kafka.acl.bootstrap-servers}")
    private val bootstrapServers: String,
) {

    fun producerProps(username: String, password: String): Map<String, Any> = mapOf(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        "security.protocol" to "SASL_PLAINTEXT",
        SaslConfigs.SASL_MECHANISM to "PLAIN",
        SaslConfigs.SASL_JAAS_CONFIG to jaasConfig(username, password),
    )

    fun consumerProps(username: String, password: String, groupId: String): Map<String, Any> = mapOf(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
        ConsumerConfig.GROUP_ID_CONFIG to groupId,
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
        ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to false,
        "security.protocol" to "SASL_PLAINTEXT",
        SaslConfigs.SASL_MECHANISM to "PLAIN",
        SaslConfigs.SASL_JAAS_CONFIG to jaasConfig(username, password),
    )

    fun adminProps(): Map<String, Any> = mapOf(
        AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
        "security.protocol" to "SASL_PLAINTEXT",
        SaslConfigs.SASL_MECHANISM to "PLAIN",
        SaslConfigs.SASL_JAAS_CONFIG to jaasConfig(KafkaAclUser.ADMIN, KafkaAclUser.ADMIN_PASSWORD),
    )

    private fun jaasConfig(username: String, password: String) =
        """org.apache.kafka.common.security.plain.PlainLoginModule required username="$username" password="$password";"""
}