package com.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class DebeziumConfig {
    @Value("${customer.datasource.host}")
    private String customerDbHost;

    @Value("${customer.datasource.database}")
    private String customerDbName;

    @Value("${customer.datasource.port}")
    private String customerDbPort;

    @Value("${customer.datasource.username}")
    private String customerDbUsername;

    @Value("${customer.datasource.password}")
    private String customerDbPassword;

    @Bean
    public Properties connector() {
        final var props = new Properties();

        props.setProperty("name", "engine-mariadb");
        props.setProperty("connector.class", "io.debezium.connector.mariadb.MariaDbConnector");

// 오프셋 저장 위치 (카프카 없이 File 사용)
        props.setProperty("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore");
        props.setProperty("offset.storage.file.filename", "/tmp/offsets.dat"); // 원하는 경로로 변경 가능
        props.setProperty("offset.flush.interval.ms", "1000");
        props.setProperty("topic.prefix", "lkdcode");
// 필수 DB 정보
        props.setProperty("database.hostname", "127.0.0.1");
        props.setProperty("database.port", "4444");
        props.setProperty("database.user", "root");
        props.setProperty("database.password", "lkdcode123!@#");
        props.setProperty("database.server.id", "223344"); // 유일해야 함 (복제 ID 개념)
        props.setProperty("database.server.name", "mariadb-server"); // 내부 topic prefix 같은 역할

        props.setProperty("database.include.list", "DebeziumTest");
        props.setProperty("table.include.list", "DebeziumTest.tb_target");

        props.setProperty("schema.history.internal", "io.debezium.storage.file.history.FileSchemaHistory");
        props.setProperty("schema.history.internal.file.filename", "/tmp/schemahistory.dat");

        return props;
    }
}
