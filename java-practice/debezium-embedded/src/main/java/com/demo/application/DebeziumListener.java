package com.demo.application;

import com.demo.repository.TbTargetRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class DebeziumListener {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final Properties config;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final TbTargetRepository tbTargetRepository;

    public DebeziumListener(
        @Qualifier("connector") Properties config,
        TbTargetRepository tbTargetRepository
    ) {
        this.config = config;
        this.tbTargetRepository = tbTargetRepository;
    }

//    @PostConstruct
    public void start() {
        executor.execute(() -> {
            try (DebeziumEngine<ChangeEvent<String, String>> engine = DebeziumEngine.create(Json.class)
                .using(config)
                .notifying(event -> {
                    System.out.println("📣 CDC Event:");
                    System.out.println(event.value());
                    var e = event.headers();
                    var ee = event.destination();
                    var key = event.key();

                    var target = event.value();

                    try {
                        Map<String, Object> map = MAPPER.readValue(target, new TypeReference<>() {
                        });

                        System.out.println(map);
                        System.out.println(map);
                        System.out.println(map);
                    } catch (JsonProcessingException ex) {
                        System.err.println("--------------------!");
                    }

                })
                .build()) {
                engine.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
