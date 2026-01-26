package dev.lkdcode.domain.repository

import dev.lkdcode.domain.value.Apple
import dev.lkdcode.domain.value.Fruit
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.time.Duration

@Repository
class FruitRepository {
    fun findByFruitId(fruitId: Long): Mono<Fruit> {
        println("FruitRepository.findByFruitId FRUIT_ID: $fruitId")

        return Mono
            .just<Fruit>(Apple(fruitId))
            .delayElement(Duration.ofSeconds(3))
    }

    fun save(fruitId: Long): Mono<Fruit> {
        println("FruitRepository.save FRUIT_ID: $fruitId")

        return Mono.just(Apple(fruitId))
    }

    fun updateByFruitId(fruitId: Long): Mono<Fruit> {
        println("FruitRepository.updateByFruitId FRUIT_ID: $fruitId")

        return Mono.just(Apple(fruitId))
    }

    fun deleteByFruitId(fruitId: Long): Mono<Fruit> {
        println("FruitRepository.deleteByFruitId FRUIT_ID: $fruitId")

        return Mono.just(Apple(fruitId))
    }
}