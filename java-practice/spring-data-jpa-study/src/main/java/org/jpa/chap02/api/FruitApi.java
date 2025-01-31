package org.jpa.chap02.api;

import lombok.RequiredArgsConstructor;
import org.jpa.chap02.entity.FruitEntity;
import org.jpa.chap02.entity.StoreEntity;
import org.jpa.chap02.repository.FruitJpaRepository;
import org.jpa.chap02.repository.StoreJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Function;

@RestController
@RequiredArgsConstructor
public class FruitApi {

    private final FruitJpaRepository fruitJpaRepository;
    private final StoreJpaRepository storeJpaRepository;

    @GetMapping("/api/fruit")
    public ResponseEntity<?> getFruit(
    ) {
        return ResponseEntity.ok(
            fruitJpaRepository.findAll()
                .stream()
                .map(convert())
                .toList()
        );
    }

    private static Function<FruitEntity, FruitDTO> convert() {
        return e -> new FruitDTO(
            e.getId().getFruitNumber(),
            e.getId().getFruitAddress(),
            e.getPrice()
        );
    }

    @PostMapping("/api/fruit")
    public ResponseEntity<?> getCreateFruit(
        @RequestBody FruitDTO request
    ) {
        final var fruitEntity = request.toEntity();

        final var storeId = StoreEntity.StoreEntityId.builder()
            .storeNumber(request.fruitNumber)
            .fruitNumber(request.fruitNumber)
            .build();

        final var storeEntity = StoreEntity.builder()
            .id(storeId)
            .fruitEntity(fruitEntity)
            .storeAddress("STORE" + fruitEntity.getId().getFruitAddress())
            .fruitAddress(fruitEntity.getId().getFruitAddress())
            .price(fruitEntity.getPrice())
            .build();

        fruitJpaRepository.save(fruitEntity);
        storeJpaRepository.save(storeEntity);

        return ResponseEntity.ok(null);
    }

    public record FruitDTO(
        String fruitNumber,
        String fruitAddress,
        String price
    ) {

        private FruitEntity toEntity() {
            final var id = convertId();
            return FruitEntity.builder()
                .id(id)
                .price(this.price)
                .build();
        }

        private FruitEntity.FruitEntityId convertId() {
            return FruitEntity.FruitEntityId.builder()
                .fruitNumber(this.fruitNumber)
                .fruitAddress(this.fruitAddress)
                .build();
        }
    }
}