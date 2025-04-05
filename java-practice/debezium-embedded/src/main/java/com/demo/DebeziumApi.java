package com.demo;

import com.demo.repository.TbTarget;
import com.demo.repository.TbTargetRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class DebeziumApi {
    private final TbTargetRepository tbTargetRepository;

    public DebeziumApi(TbTargetRepository tbTargetRepository) {
        this.tbTargetRepository = tbTargetRepository;
    }

    @GetMapping("/debezium")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok(tbTargetRepository.findAll());
    }

    @PostMapping("/debezium")
    public ResponseEntity<?> insert() {
        tbTargetRepository.save(
            new TbTarget(null, "hihihi", LocalDateTime.now())
        );

        return ResponseEntity.ok((Void) null);
    }
}
