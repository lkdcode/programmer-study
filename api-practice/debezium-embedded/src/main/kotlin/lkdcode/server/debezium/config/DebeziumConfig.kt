package lkdcode.server.debezium.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class DebeziumConfig {

    @Bean("debeziumConnector")
    fun debeziumConnector(): Properties = Properties()
        .apply {
            put("name", "lkdcode-debezium")
            put("connector.class", "io.debezium.connector.mysql.MySqlConnector")
            put("topic.prefix", "lkdcode")

            put("snapshot.mode", "no_data")
            put("snapshot.locking.mode", "none")

            put("database.hostname", "localhost")
            put("database.port", "13306")
            put("database.user", "root")
            put("database.password", "lkdcode")
            put("database.server.id", "1234")
            put("database.connectionTimeZone", "Asia/Seoul")

            put("database.include.list", "lkdcode")
            put("table.include.list", "lkdcode.banana")

            put("offset.storage", "io.debezium.storage.jdbc.offset.JdbcOffsetBackingStore")
            put("offset.storage.jdbc.connection.url", "jdbc:mysql://localhost:13306/lkdcode")
            put("offset.storage.jdbc.connection.user", "root")
            put("offset.storage.jdbc.connection.password", "lkdcode")
            put("offset.storage.jdbc.table.name", "debezium_offsets")

            put("schema.history.internal", "io.debezium.storage.jdbc.history.JdbcSchemaHistory")
            put("schema.history.internal.jdbc.connection.url", "jdbc:mysql://localhost:13306/lkdcode")
            put("schema.history.internal.jdbc.connection.user", "root")
            put("schema.history.internal.jdbc.connection.password", "lkdcode")
            put("schema.history.internal.jdbc.table.name", "debezium_schema_history")

            put("decimal.handling.mode", "string")
        }
}