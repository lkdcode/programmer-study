package org.jpa.chap02.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "tb_fruit")
@NoArgsConstructor(access = PROTECTED)
public class FruitEntity {

    @Getter
    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor(access = PROTECTED)
    public static class FruitEntityId implements Serializable {
        @Column(name = "FRUIT_NUMBER", nullable = false, length = 20)
        private String fruitNumber;

        @Column(name = "FRUIT_ADDRESS", nullable = false, length = 20)
        private String fruitAddress;

        @Builder
        public FruitEntityId(String fruitNumber, String fruitAddress) {
            this.fruitNumber = fruitNumber;
            this.fruitAddress = fruitAddress;
        }
    }

    @EmbeddedId
    private FruitEntityId id;

    @Column(name = "PRICE", nullable = false, length = 20)
    private String price;

    @Builder
    public FruitEntity(FruitEntityId id, String price) {
        this.id = id;
        this.price = price;
    }
}