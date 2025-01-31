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
@Table(name = "tb_store")
@NoArgsConstructor(access = PROTECTED)
public class StoreEntity {

    @Getter
    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor(access = PROTECTED)
    public static class StoreEntityId implements Serializable {
        @Column(name = "STORE_NUMBER", nullable = false, length = 20)
        private String storeNumber;

        @Column(name = "FRUIT_NUMBER", nullable = false, length = 20)
        private String fruitNumber;

        @Builder
        public StoreEntityId(String storeNumber, String fruitNumber) {
            this.storeNumber = storeNumber;
            this.fruitNumber = fruitNumber;
        }
    }

    @EmbeddedId
    private StoreEntityId id;

    @Column(name = "FRUIT_ADDRESS", nullable = false, length = 20)
    private String fruitAddress;
    @Column(name = "STORE_ADDRESS", nullable = false, length = 20)
    private String storeAddress;

    @Column(name = "PRICE", nullable = false, length = 20)
    private String price;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "FRUIT_NUMBER", referencedColumnName = "FRUIT_NUMBER", updatable = false, insertable = false),
        @JoinColumn(name = "FRUIT_ADDRESS", referencedColumnName = "FRUIT_ADDRESS", updatable = false, insertable = false)
    })
    private FruitEntity fruitEntity;

    @Builder
    public StoreEntity(StoreEntityId id, String fruitAddress, String storeAddress, String price, FruitEntity fruitEntity) {
        this.id = id;
        this.fruitAddress = fruitAddress;
        this.storeAddress = storeAddress;
        this.price = price;
        this.fruitEntity = fruitEntity;
    }
}